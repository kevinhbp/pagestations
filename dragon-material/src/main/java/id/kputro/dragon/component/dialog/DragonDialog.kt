package id.kputro.dragon.component.dialog

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import id.kputro.dragon.LOG_TAG
import java.lang.Exception
import java.lang.ref.WeakReference

typealias DragonDialogDeclaration = DragonDialog.() -> Unit

fun initDragonDialog(mDeclaration: DragonDialogDeclaration? = null): DragonDialog {
  val mDialog = DragonDialog.init()
  mDeclaration?.invoke(mDialog)
  return mDialog
}

class DragonDialog : LifecycleObserver {

  private var mReference: WeakReference<Activity>? = null

  private var mToast: Toast? = null

  private var mPaused: Boolean = false

  // --
  fun setReference(reference: WeakReference<Activity>) {
    mReference = reference
  }

  fun getReference(): WeakReference<Activity>? {
    if (mReference == null) {
      Log.e(LOG_TAG, "Activity Reference is not set, set the reference first on onCreate")
    }
    return mReference
  }

  // --
  fun showMessage(title: String, message: String, onDismiss: (() -> Unit)?) {
    if (title.isEmpty() && message.isEmpty()) return
    if (mPaused) return
    val mContext = getReference()?.get() ?: return
    // todo
  }

  fun showConfirmation(
    title: String,
    message: String,
    positiveButton: String,
    negativeButton: String,
    onConfirmed: (isPositive: Boolean) -> Unit
  ) {
    if (title.isEmpty() && message.isEmpty()) return
    if (positiveButton.isEmpty() && negativeButton.isEmpty()) return
    if (mPaused) return
    val mContext = getReference()?.get() ?: return
    // todo
  }

  fun showToast(message: String, doLong: Boolean) {
    if (mPaused) return
    val mContext = getReference()?.get() ?: return
    val mLength = if (doLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    mToast?.cancel()
    mToast = Toast.makeText(mContext, message, mLength)
    mToast?.setGravity(Gravity.CENTER, 0, 0)
    try {
      mToast?.show()
    } catch (e: Exception) {
      Log.e(LOG_TAG, "Cannot show toast - $e")
    }
  }

  // --
  @OnLifecycleEvent(ON_PAUSE)
  fun onPause() {
    // will block dialog request when activity is on paused
    mPaused = true
    mToast?.cancel()
  }

  @OnLifecycleEvent(ON_RESUME)
  fun onResume() {
    // cancel block flag when activity is on resume
    mPaused = false
  }

  // --
  companion object {
    private lateinit var app: DragonDialog

    fun get(): DragonDialog {
      if (!::app.isInitialized) {
        throw RuntimeException("Dragon Dialog is not started..")
      }
      return app
    }

    fun init(): DragonDialog {
      app = DragonDialog()
      return app
    }
  }
}