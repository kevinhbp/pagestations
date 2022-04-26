package id.kputro.pagestations.module

import android.util.Log

object Log {

  private const val LOG_TAG = "PageStation"

  private var enabled: Boolean = false

  fun enableLog(enable: Boolean = true) {
    this.enabled = enable
  }

  fun d(message: String) {
    if (!enabled) return
    Log.d(LOG_TAG, message)
  }

  fun e(message: String) {
    if (!enabled) return
    Log.e(LOG_TAG, message)
  }

}