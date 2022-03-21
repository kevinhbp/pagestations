package id.kputro.hello.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import id.kputro.hello.utils.applink.Applink.doAppLink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
  }

  // --
  override fun showLoading(show: Boolean) {
    // todo
  }

  override fun goTo(target: String) {
    this.doAppLink(target)
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
}