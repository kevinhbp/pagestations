package id.kputro.bootstrap.dialog

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
import id.kputro.bootstrap.BootstrapApplication
import id.kputro.bootstrap.LOG_TAG
import id.kputro.bootstrap.R.dimen
import id.kputro.bootstrap.R.id
import id.kputro.bootstrap.R.layout
import id.kputro.bootstrap.R.string
import java.lang.ref.WeakReference

typealias DialogReferenceDeclaration = Dialog.() -> Unit

fun setupDialogReference(declaration: DialogReferenceDeclaration) {
  val mDialog = BootstrapApplication.get().mDialog
  declaration.invoke(mDialog)
}

fun showMessageDialog(title: String, message: String, onDismiss: (() -> Unit)?) {
  val mDialog = BootstrapApplication.get().mDialog
  mDialog.showMessage(title, message, onDismiss)
}

fun showConfirmationDialog(
  title: String,
  message: String,
  positiveButton: String,
  negativeButton: String,
  onConfirmed: (isPositive: Boolean) -> Unit
) {
  val mDialog = BootstrapApplication.get().mDialog
  mDialog.showConfirmation(title, message, positiveButton, negativeButton, onConfirmed)
}

fun showToastMessage(message: String, doLong: Boolean) {
  val mDialog = BootstrapApplication.get().mDialog
  mDialog.showToast(message, doLong)
}

fun showLoadingDialog(show: Boolean) {
  val mDialog = BootstrapApplication.get().mDialog
  mDialog.showLoading(show)
}

class Dialog : LifecycleObserver {

  private var mReference: WeakReference<Activity>? = null

  private var mToast: Toast? = null

  private var mMessageDialog: MaterialDialog? = null

  private var mLoadingDialog: MaterialDialog? = null

  private var mPaused: Boolean = false

  fun setReference(ref: WeakReference<Activity>) {
    mReference = ref
  }

  private fun getReference(): WeakReference<Activity>? {
    if (mReference == null) {
      Log.e(
        LOG_TAG,
        "Activity Reference is not set yet, set the reference activity first before use this class."
      )
    }
    return mReference
  }

  // --
  @SuppressLint("InflateParams")
  fun showMessage(title: String, message: String, onDismiss: (() -> Unit)?) {
    if (title.isEmpty() && message.isEmpty()) return
    if (mPaused) return
    val mActivity = getReference()?.get() ?: return
    val mView = mActivity.layoutInflater.inflate(layout.bs_dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).visibility = View.GONE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).text = mActivity.getString(string.bs_ok)
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility =
      if (title.isEmpty()) View.GONE else View.VISIBLE
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
      .cornerRadius(res = dimen.bs_radius_small)
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
    val mView = mActivity.layoutInflater.inflate(layout.bs_dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.GONE
    mView.getView<TextView>(id.btn_negative).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).text = negativeButton
    mView.getView<TextView>(id.btn_positive).text = positiveButton
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility =
      if (title.isEmpty()) View.GONE else View.VISIBLE
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
      .cornerRadius(res = dimen.bs_radius_small)
    try {
      mMessageDialog?.show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @SuppressLint("InflateParams")
  fun showLoading(show: Boolean) {
    if (mPaused) return
    val mActivity = getReference()?.get() ?: return
    if (mLoadingDialog?.isShowing == true) mLoadingDialog?.dismiss()
    if (!show) return
    if (mLoadingDialog == null) {
      val mView = mActivity.layoutInflater.inflate(layout.bs_dialog_loading, null, false)
      mView.getView<TextView>(id.tv_body).text = mActivity.getString(string.bs_please_wait)
      mLoadingDialog = MaterialDialog(mActivity)
        .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
        .cancelable(false)
        .cancelOnTouchOutside(false)
        .cornerRadius(res = dimen.bs_radius_small)
    }
    try {
      if (!mLoadingDialog?.isShowing!!) {
        mLoadingDialog?.show()
      }
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
  private fun <T : View> View.getView(id: Int): T {
    return this.findViewById(id)
  }
}