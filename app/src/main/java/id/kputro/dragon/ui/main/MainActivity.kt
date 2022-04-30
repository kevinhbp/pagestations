package id.kputro.dragon.ui.main

import android.os.Bundle
import id.kputro.dragon.R.layout
import id.kputro.dragon.databinding.MainActivityBinding
import id.kputro.dragon.ui.base.BaseActivity
import id.kputro.dragon.utils.applink.getDashboardPage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainActivityBinding>(layout.main_activity),
  MainContract.MainViewContract {

  private lateinit var binding: MainActivityBinding

  private val mainViewModel: MainViewModel by viewModel()

  override fun initDataBinding(binding: MainActivityBinding) {
    this.binding = binding
    this.binding.mainViewModel = this.mainViewModel
    this.binding.executePendingBindings()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainViewModel.init(this)
  }

  override fun onCompleteDraw() {
    super.onCompleteDraw()
    mainViewModel.start()
  }

  override fun initMainView() {
    // not used at the moment..
  }

  override fun delayToDashboardAfter() {
    delayFunction(1000L) {
      goTo(getDashboardPage())
      finish()
    }
  }
}