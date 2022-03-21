package id.kputro.hello.utils.applink

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import id.kputro.hello.ui.dashboard.DashboardActivity
import id.kputro.hello.utils.const.Global
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

object Applink {
  private val mInternalBrowserSchemes = arrayOf(Route.HTTPS_SCHEME, Route.HTTP_SCHEME)
  private val mSupportedInternalSchemes =
    arrayOf(Route.APP_SCHEME, Route.HTTPS_SCHEME, Route.HTTP_SCHEME)

  fun Context.doAppLink(target: String?) {
    if (target.isNullOrEmpty()) return
    preventDoubleCall {
      Log.d(Global.LOG_TAG, "doAppLink(wTarget= $target)")
      routeAppLink(this, Uri.parse(target))
    }
  }

  fun Context.doDial(mPhoneNumber: String) {
    if (mPhoneNumber.isEmpty()) return
    val mUri = Uri.parse("tel:$mPhoneNumber")
    val mIntent = Intent(Intent.ACTION_DIAL)
    mIntent.data = mUri
    ContextCompat.startActivity(this, mIntent, null)
  }

  fun Context.doEmail(mTarget: String, mSubject: String, mBody: String) {
    val mIntent = Intent(Intent.ACTION_SENDTO)
    mIntent.apply {
      putExtra(Intent.EXTRA_SUBJECT, mSubject)
      putExtra(Intent.EXTRA_TEXT, mBody)
      data = Uri.parse("mailto:$mTarget")
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    ContextCompat.startActivity(this, mIntent, null)
  }

  fun Context.doWhatsApp(mPhone: String, mMessage: String) {
    val mFormattedPhone =
      if (mPhone.startsWith('0', true)) mPhone.replaceFirst("0", "+62") else mPhone
    val mIntent = Intent(Intent.ACTION_VIEW)
    val mEncodedMessage = URLEncoder.encode(mMessage, "UTF-8")
    val mUrl = "https://api.whatsapp.com/send?phone=$mFormattedPhone&text=$mEncodedMessage"
    mIntent.setPackage("com.whatsapp")
    mIntent.data = Uri.parse(mUrl)
    try {
      ContextCompat.startActivity(this, mIntent, null)
    } catch (e: Exception) {
      Log.e(Global.LOG_TAG, "On doWhatsApp error: $e")
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

  private fun routeAppLink(mContext: Context, mUri: Uri): Boolean {
    if (!mSupportedInternalSchemes.contains(mUri.scheme)) {
      val mIntent = Intent(Intent.ACTION_VIEW)
      mIntent.data = mUri
      ContextCompat.startActivity(mContext, mIntent, null)
      return false
    }
    if (mInternalBrowserSchemes.contains(mUri.scheme)) {

      return true
    }

    doRouteInternalAppLink(mContext, mUri)
    return true
  }

  private fun doRouteInternalAppLink(mContext: Context, mUri: Uri) {
    var mTarget: Intent? = null
    val mData = HashMap<String, String?>()
    mData["deeplinkHost"] = mUri.host
    mUri.queryParameterNames.forEach { q ->
      mData[q] = mUri.getQueryParameter(q)
    }
    when (mUri.host) {
      Route.PAGE_DASHBOARD -> mTarget = DashboardActivity.newIntent(mContext)
    }
    mData.forEach {
      mTarget?.putExtra(it.key, it.value)
    }
    if (mTarget == null) return
    ContextCompat.startActivity(mContext, mTarget, null)
  }

  private val mMainScope: CoroutineScope by lazy { MainScope() }
  private var mJob: Job? = null
  private var mLock: Boolean = false

  private fun preventDoubleCall(mDelay: Long = 500L, mCallback: () -> Unit) {
    if (mLock) return
    mLock = true
    mCallback.invoke()
    mJob?.cancel()
    mJob = mMainScope.launch {
      delay(mDelay)
      mLock = false
    }
  }
}

object Route {
  const val APP_SCHEME = "kputro"
  const val HTTP_SCHEME = "http"
  const val HTTPS_SCHEME = "https"

  const val CLOSE_PAGE = "close_page"
  const val CLOSE_APP = "close_app"
  const val NAVIGATION_BACK = "navigation_back"

  const val PAGE_DASHBOARD = "page_dashboard"
  const val PAGE_SETTINGS = "page_settings"
  const val PAGE_PERMISSIONS = "page_permission"
  const val PAGE_NOTIFICATIONS = "page_notifications"
  const val PAGE_BROWSER = "page_browser"

  const val PAGE_APPLINK = "page_applink"
  const val PAGE_APPLINK_SAVED = "page_applink_saved"

  const val PAGE_ABOUT_ME = "page_about_me"

  const val PAGE_LOGIN = "page_login"
  const val PAGE_PROFILE = "page_profile"
  const val PAGE_REGISTRATION = "page_registration"
}

fun getAppScheme(): String {
  return Route.APP_SCHEME + "://"
}

fun getDashboardPageApplink(): String {
  return getAppScheme() + Route.PAGE_DASHBOARD
}