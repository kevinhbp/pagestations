package id.kputro.hello.ui.main

import android.os.Bundle
import id.kputro.hello.R.layout
import id.kputro.hello.databinding.ActivityMainBinding
import id.kputro.hello.ui.base.BaseActivity
import id.kputro.hello.utils.applink.getDashboardPageApplink
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(layout.main_activity),
  MainContract.MainViewContract {

  private lateinit var binding: ActivityMainBinding

  private val mainViewModel : MainViewModel by viewModel()

  override fun initDataBinding(binding: ActivityMainBinding) {
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
    delayFunction(2000L) {
      goTo(getDashboardPageApplink())
      finish()
    }
  }
}