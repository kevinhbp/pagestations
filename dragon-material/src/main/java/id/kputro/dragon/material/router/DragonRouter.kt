package id.kputro.dragon.material.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import id.kputro.dragon.material.LOG_TAG
import id.kputro.dragon.material.router.module.RouterModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias DragonRouterDeclaration = DragonRouter.() -> Unit

fun initDragonRouter(mDeclaration: DragonRouterDeclaration? = null): DragonRouter {
  val mRouter = DragonRouter.init()
  mDeclaration?.invoke(mRouter)
  return mRouter
}

class DragonRouter {

  private var mRouterModule: RouterModule? = null

  fun module(mModule: RouterModule): DragonRouter {
    mRouterModule = mModule
    return this
  }

  fun route(context: Context, target: String) {
    if (mRouterModule == null) {
      Log.e(LOG_TAG, "Dragon Module is not yet declared")
      return
    }

    preventDoubleCall {
      Log.d(LOG_TAG, "Route Applink -> $target")
      routeLink(context, Uri.parse(target))
    }
  }

  private fun routeLink(context: Context, uri: Uri) {
    if (mRouterModule == null) return
    val targetScheme = uri.scheme ?: return
    val isRegistered = mRouterModule!!.isSchemeSupported(targetScheme)
    if (!isRegistered) {
      Log.e(LOG_TAG, "Scheme is not registered, route to external..")
      routeToExternal(context, uri)
      return
    }
    Log.e(LOG_TAG, "Scheme is registered, route to internal..")
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
    val mClass = mRouterModule!!.findClass(mScheme, mHost)
    if (mClass == null) {
      Log.e(
        LOG_TAG,
        "Target Class with Scheme $mScheme and Host $mHost is NOT FOUND"
      )
      return
    }
    val className = mClass.canonicalName
    Log.d(
      LOG_TAG,
      "Target Class with Scheme \"$mScheme\" and Host \"$mHost\" is FOUND with Target Class \"$className\""
    )
    val mTarget: Intent = mClass.getIntent(mContext)
    val mData = HashMap<String, String?>()
    mData["mHost"] = mUri.host
    mUri.queryParameterNames.forEach { q ->
      mData[q] = mUri.getQueryParameter(q)
    }
    mData.forEach {
      mTarget.putExtra(it.key, it.value)
    }
    try {
      ContextCompat.startActivity(mContext, mTarget, null)
      Log.e(LOG_TAG, "Starting Activity: $className")
    } catch (e: Exception) {
      Log.e(LOG_TAG, "Cannot Start Activity: $e")
    }
  }

  private fun Class<*>.getIntent(mContext: Context): Intent {
    return Intent(mContext, this)
  }

  companion object {
    private lateinit var app: DragonRouter

    fun get(): DragonRouter {
      if (!Companion::app.isInitialized) {
        throw RuntimeException("Dragon Router is not started..")
      }
      return app
    }


    fun init(): DragonRouter {
      app = DragonRouter()
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