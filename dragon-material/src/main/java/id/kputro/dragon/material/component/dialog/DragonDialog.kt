package id.kputro.dragon.material.component.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import id.kputro.dragon.material.LOG_TAG
import id.kputro.dragon.material.R.dimen
import id.kputro.dragon.material.R.id
import id.kputro.dragon.material.R.layout
import id.kputro.dragon.material.R.string
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

  private var mMessageDialog: MaterialDialog? = null

  private var mPaused: Boolean = false

  // --
  fun setReference(reference: WeakReference<Activity>) {
    mReference = reference
  }

  private fun getReference(): WeakReference<Activity>? {
    if (mReference == null) {
      Log.e(LOG_TAG, "Activity Reference is not set, set the reference first on onCreate")
    }
    return mReference
  }

  // --
  @SuppressLint("InflateParams")
  fun showMessage(title: String, message: String, onDismiss: (() -> Unit)?) {
    if (title.isEmpty() && message.isEmpty()) return
    if (mPaused) return
    val mActivity = getReference()?.get() ?: return
    val mView = mActivity.layoutInflater.inflate(layout.dm_dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).visibility = View.GONE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).text = mActivity.getString(string.dm_ok)
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility = if (title.isEmpty()) View.GONE else View.VISIBLE
    mView.getView<TextView>(id.btn_positive).setOnClickListener {
      mMessageDialog?.dismiss()
      onDismiss?.invoke()
    }
    mView.getView<TextView>(id.btn_negative).setOnClickListener {
      mMessageDialog?.dismiss()
      onDismiss?.invoke()
    }
    mMessageDialog?.dismiss()
    mMessageDialog = MaterialDialog(mActivity)
      .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
      .cancelable(false)
      .cancelOnTouchOutside(false)
      .cornerRadius(res = dimen.dm_radius_small)
    mMessageDialog?.setOnCancelListener {
      onDismiss?.invoke()
    }
    try {
      mMessageDialog?.show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @SuppressLint("InflateParams")
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
    val mActivity = getReference()?.get() ?: return
    val mView = mActivity.layoutInflater.inflate(layout.dm_dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.GONE
    mView.getView<TextView>(id.btn_negative).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).text = negativeButton
    mView.getView<TextView>(id.btn_positive).text = positiveButton
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility = if (title.isEmpty()) View.GONE else View.VISIBLE
    mView.getView<TextView>(id.btn_positive).setOnClickListener {
      mMessageDialog?.dismiss()
      onConfirmed.invoke(true)
    }
    mView.getView<TextView>(id.btn_negative).setOnClickListener {
      mMessageDialog?.dismiss()
      onConfirmed.invoke(false)
    }
    mMessageDialog?.dismiss()
    mMessageDialog = MaterialDialog(mActivity)
      .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
      .cancelable(false)
      .cancelOnTouchOutside(false)
      .cornerRadius(res = dimen.dm_radius_small)
    try {
      mMessageDialog?.show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
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
      if (!Companion::app.isInitialized) {
        throw RuntimeException("Dragon Dialog is not started..")
      }
      return app
    }

    fun init(): DragonDialog {
      app = DragonDialog()
      return app
    }
  }

  // --
  private fun <T: View> View.getView(id: Int): T {
    return this.findViewById(id)
  }
}