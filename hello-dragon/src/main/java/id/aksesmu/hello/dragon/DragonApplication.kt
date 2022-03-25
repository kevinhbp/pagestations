package id.aksesmu.hello.dragon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import id.aksesmu.hello.dragon.module.DragonModule
import id.aksesmu.hello.dragon.tools.DragonConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

/**
 * Dragon Router Application
 *
 * @author Kevin Putro
 */

typealias DragonAppDeclaration = DragonApplication.() -> Unit

fun dragonApplication(appDeclaration: DragonAppDeclaration? = null): DragonApplication {
  val dragonApplication = DragonApplication.init()
  appDeclaration?.invoke(dragonApplication)
  return dragonApplication
}

fun Context.doDialPhoneNumber(phoneNumber: String) {
  if (phoneNumber.isEmpty()) return
  val uri = Uri.parse("tel:$phoneNumber")
  val intent = Intent(Intent.ACTION_DIAL)
  intent.data = uri
  ContextCompat.startActivity(this, intent, null)
}

fun Context.doWriteEmail(to: String, subject: String, body: String) {
  val intent = Intent(Intent.ACTION_SENDTO)
  intent.apply {
    putExtra(Intent.EXTRA_SUBJECT, subject)
    putExtra(Intent.EXTRA_TEXT, body)
    data = Uri.parse("mailto:$to")
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }
  ContextCompat.startActivity(this, intent, null)
}

fun Context.doSendWhatsApp(phone: String, message: String) {
  val isStartWithZero = phone.startsWith('0', true)
  val replacedFirstZeroPhone = phone.replaceFirst("0", "+62")
  val mPhone = if (isStartWithZero) replacedFirstZeroPhone else phone
  val mIntent = Intent(Intent.ACTION_VIEW)
  val mEncodedMessage = URLEncoder.encode(message, "UTF-8")
  val mUrl = "https://api.whatsapp.com/send?phone=$mPhone&text=$mEncodedMessage"
  mIntent.setPackage("com.whatsapp")
  mIntent.data = Uri.parse(mUrl)
  try {
    ContextCompat.startActivity(this, mIntent, null)
  } catch (e: Exception) {
    Log.e(DragonConstants.LOG_TAG, "doSendWhatsApp error: $e")
  }
}

fun Context.doShareText(mText: String, mTitle: String?) {
  if (mText.isEmpty()) return
  val mIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, mText)
    type = "text/plain"
  }
  val mShareIntent = Intent.createChooser(mIntent, mTitle)
  ContextCompat.startActivity(this, mShareIntent, null)
}

fun String.getUriObject(): Uri? {
  if (this.isEmpty()) return null
  return try {
    Uri.parse(this)
  } catch (e: IllegalStateException) {
    e.printStackTrace()
    null
  }
}

class DragonApplication {

  private var mDragonModule: DragonModule? = null

  fun module(mModule: DragonModule): DragonApplication {
    mDragonModule = mModule
    return this
  }

  fun route(context: Context, target: String) {
    if (mDragonModule == null) {
      Log.e(DragonConstants.LOG_TAG, "Dragon Module is not yet declared")
      return
    }

    preventDoubleCall {
      Log.d(DragonConstants.LOG_TAG, "Route Applink -> $target")
      routeLink(context, Uri.parse(target))
    }
  }

  private fun routeLink(context: Context, uri: Uri) {
    if (mDragonModule == null) return
    val targetScheme = uri.scheme ?: return
    val isRegistered = mDragonModule!!.isSchemeSupported(targetScheme)
    if (!isRegistered) {
      Log.e(DragonConstants.LOG_TAG, "Scheme is not registered, route to external..")
      routeToExternal(context, uri)
      return
    }
    Log.e(DragonConstants.LOG_TAG, "Scheme is registered, route to internal..")
    routeToInternal(context, uri)
  }

  private fun routeToExternal(context: Context, uri: Uri) {
    val mIntent = Intent(Intent.ACTION_VIEW)
    mIntent.data = uri
    ContextCompat.startActivity(context, mIntent, null)
  }

  private fun routeToInternal(mContext: Context, mUri: Uri) {
    val mScheme = mUri.scheme
    val mHost = mUri.host
    val mClass = mDragonModule!!.findClass(mScheme, mHost)
    if (mClass == null) {
      Log.e(DragonConstants.LOG_TAG, "Target Class with Scheme $mScheme and Host $mHost is NOT FOUND")
      return
    }
    val className = mClass.canonicalName
    Log.d(DragonConstants.LOG_TAG, "Target Class with Scheme \"$mScheme\" and Host \"$mHost\" is FOUND with Target Class \"$className\"")
    val mTarget: Intent = mClass.getIntent(mContext)
    val mData = HashMap<String, String?> ()
    mData["mHost"] = mUri.host
    mUri.queryParameterNames.forEach { q ->
      mData[q] = mUri.getQueryParameter(q)
    }
    mData.forEach {
      mTarget.putExtra(it.key, it.value)
    }
    try {
      ContextCompat.startActivity(mContext, mTarget, null)
      Log.e(DragonConstants.LOG_TAG, "Starting Activity: $className")
    } catch (e: Exception) {
      Log.e(DragonConstants.LOG_TAG, "Cannot Start Activity: $e")
    }
  }

  private fun Class<*>.getIntent(mContext: Context): Intent {
    return Intent(mContext, this)
  }

  companion object {
    private lateinit var app: DragonApplication

    fun get(): DragonApplication {
      if (!::app.isInitialized) {
        throw RuntimeException("Dragon Application is not started..")
      }
      return app
    }


    fun init(): DragonApplication {
      app = DragonApplication()
      return app
    }
  }

  // --
  private val mainScope: CoroutineScope by lazy { MainScope() }
  private var mainJob: Job? = null
  private var jobRunning: Boolean = false

  private fun preventDoubleCall(mDelay: Long = 500L, mCallback: () -> Unit) {
    if (jobRunning) return
    jobRunning = true
    mCallback.invoke()
    mainJob?.cancel()
    mainJob = mainScope.launch {
      delay(mDelay)
      jobRunning = false
    }
  }
}