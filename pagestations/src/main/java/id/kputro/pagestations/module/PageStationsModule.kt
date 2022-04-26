package id.kputro.pagestations.module

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Page Stations Module
 * Gather compose module definitions
 *
 * @author Kevin Putro
 * @since Feb 22, 2022
 */

class PageStationsModule {

  private val mMappings: HashMap<String, HashMap<String, Class<*>>> = hashMapOf()

  private val mSchemes: ArrayList<String> = arrayListOf()

  private var mRouteExternalIfNotRegistered: Boolean = false

  fun printAllPossibleRoute() {
    mSchemes.forEach {
      Log.d("------")
      val map = getMappingOnScheme(it)
      map?.keys?.forEach { k ->
        val className = map[k]?.simpleName ?: "unknown_class_name"
        Log.d("$it://$k -> $className")
      }
      Log.d("------")
    }
  }

  fun registerActivity(scheme: String, host: String, cls: Class<*>) {
    val className = cls.simpleName
    Log.d("Register new activity $className -> $scheme://$host")
    val map = getMappingOnScheme(scheme)
    if (map == null) {
      val nMap = HashMap<String, Class<*>>()
      nMap[host] = cls
      mMappings[scheme] = nMap
      mSchemes.add(scheme)
      return
    }
    map[host] = cls
  }

  fun enableLog() {
    Log.enableLog(true)
  }

  fun disableLog() {
    Log.enableLog(false)
  }

  private fun findRegisteredClass(scheme: String?, host: String?): Class<*>? {
    if (scheme.isNullOrEmpty()) return null
    if (host.isNullOrEmpty()) return null
    return getMappingOnScheme(scheme).getRegisteredClass(host)
  }

  private fun String.isSupportedScheme(): Boolean {
    return mSchemes.contains(this)
  }

  // --
  companion object {
    private lateinit var app: PageStationsModule

    fun get(): PageStationsModule {
      if (!::app.isInitialized) {
        throw RuntimeException("Page Stations Module is not started..")
      }

      return app
    }

    fun init(): PageStationsModule {
      app = PageStationsModule()
      return app
    }
  }

  // --
  private fun getMappingOnScheme(scheme: String): HashMap<String, Class<*>>? {
    return mMappings[scheme]
  }

  private fun HashMap<String, Class<*>>?.getRegisteredClass(host: String): Class<*>? {
    val mappings = this ?: return null
    return mappings[host]
  }

  // --
  private val dcp = DoubleCallPreventor()

  fun route(context: Context, target: String) {
    dcp.preventDoubleCall {
      routeTo(context, Uri.parse(target))
    }
  }

  private fun routeTo(context: Context, uri: Uri) {
    val targetScheme = uri.scheme ?: return
    val isRegistered = targetScheme.isSupportedScheme()
    if (!isRegistered) {
      if (mRouteExternalIfNotRegistered) {
        Log.d("Scheme is not registered, route to external..")
        routeToExternal(context, uri)
      } else {
        Log.d("Scheme is not registered, route to external is disabled.")
      }
      return
    }
    routeToInternal(context, uri)
  }

  private fun routeToExternal(context: Context, uri: Uri) {
    val mIntent = Intent(Intent.ACTION_VIEW)
    mIntent.data = uri
    ContextCompat.startActivity(context, mIntent, null)
  }

  private fun routeToInternal(context: Context, uri: Uri) {
    val mScheme = uri.scheme
    val mHost = uri.host
    val mClass = findRegisteredClass(mScheme, mHost)
    if (mClass == null) {
      Log.e("Target link $mScheme://$mHost -> Target Class is NOT FOUND.")
      return
    }
    val mClassName = mClass.simpleName
    Log.d("Target link $mScheme://$mHost -> Target Class $mClassName.")

    val mTarget: Intent = mClass.getIntent(context)
    val mData = HashMap<String, String?>()
    mData["host"] = mHost
    uri.queryParameterNames.forEach { q ->
      mData[q] = uri.getQueryParameter(q)
    }
    mData.forEach {
      mTarget.putExtra(it.key, it.value)
    }

    try {
      ContextCompat.startActivity(context, mTarget, null)
      Log.d("Activity started ($mClassName)")
    } catch (e: Exception) {
      Log.e("Cannot start activity cause: $e")
    }
  }

  private fun Class<*>.getIntent(mContext: Context): Intent {
    return Intent(mContext, this)
  }

  inner class DoubleCallPreventor {
    private val mainScope: CoroutineScope by lazy { MainScope() }
    private var mainJob: Job? = null
    private var jobRunning: Boolean = false

    fun preventDoubleCall(mDelay: Long = 500L, mCallback: () -> Unit) {
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
}