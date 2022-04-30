package id.kputro.dragon.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import id.kputro.bootstrap.dialog.setupDialogReference
import id.kputro.bootstrap.dialog.showConfirmationDialog
import id.kputro.bootstrap.dialog.showLoadingDialog
import id.kputro.bootstrap.dialog.showMessageDialog
import id.kputro.bootstrap.dialog.showToastMessage
import id.kputro.dragon.R.string
import id.kputro.dragon.core.extension.replaceIfNull
import id.kputro.pagestations.dsl.navigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

abstract class BaseActivity<in T : ViewDataBinding>(private val mLayoutResId: Int) :
  AppCompatActivity(), BaseView {

  // --
  private fun setupDefaultSettings() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
  }

  private fun setupLayoutObserver(root: View) {
    root.viewTreeObserver
      .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          root.viewTreeObserver.removeOnGlobalLayoutListener(this)
          onCompleteDraw()
        }
      })
  }

  // --
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: T = DataBindingUtil.setContentView(this, mLayoutResId)
    initDataBinding(binding)

    setupDefaultSettings()
    setupLayoutObserver(binding.root)
    val mActivity = this
    setupDialogReference {
      setReference(WeakReference(mActivity))
      mActivity.lifecycle.addObserver(this)
    }
  }

  // --
  override fun showLoading(show: Boolean) {
    showLoadingDialog(show)
  }

  override fun showMessage(title: String?, message: String, onDismiss: (() -> Unit)?) {
    showMessageDialog(title.replaceIfNull(), message, onDismiss)
  }

  override fun showToast(message: String, doLong: Boolean) {
    showToastMessage(message, doLong)
  }

  // --
  override fun goTo(target: String) {
    this.navigateTo(target)
  }

  // --
  open fun onCompleteDraw() {}

  // --
  abstract fun initDataBinding(binding: T)

  // --
  private var mainScope: CoroutineScope = MainScope()
  private var mJob: Job? = null
  private var mLockFlag = false
  fun delayFunction(delay: Long = 500L, callback: () -> Unit) {
    if (mLockFlag) return
    mLockFlag = true
    mJob?.cancel()
    mJob = mainScope.launch {
      delay(delay)
      callback.invoke()
      mLockFlag = false
    }
  }

  // --
  private var mClosePageConfirmationType = -1

  private fun setClosePageConfirmationType(type: Int) {
    if (type != 0 && type != 1) {
      mClosePageConfirmationType = -1
      return
    }
    this.mClosePageConfirmationType = type
  }

  fun setClosePageConfirmationApp() {
    setClosePageConfirmationType(0)
  }

  fun setClosePageConfirmationPage() {
    setClosePageConfirmationType(1)
  }

  override fun onBackPressed() {
    val mTitle = getString(string.fo_close_confirmation)
    val mMessage = if (mClosePageConfirmationType == 0) {
      getString(string.fo_are_you_sure_close_app)
    } else {
      getString(string.fo_are_you_sure_close_page)
    }
    val mPositive = getString(string.fo_yes)
    val mNegative = getString(string.fo_no)
    showConfirmationDialog(mTitle, mMessage, mPositive, mNegative) {
      if (it) {
        super.onBackPressed()
      }
    }
  }
}