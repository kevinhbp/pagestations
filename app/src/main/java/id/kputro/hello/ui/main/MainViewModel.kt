package id.kputro.hello.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import id.kputro.hello.dimodule.ContentService
import id.kputro.hello.ui.base.BaseView
import id.kputro.hello.ui.base.BaseViewModel
import id.kputro.hello.ui.main.MainContract.MainViewContract

interface MainContract {

  interface MainViewModelContract : BaseViewModel<MainViewContract> {

  }

  interface MainViewContract : BaseView {
    fun initMainView()
    fun delayToDashboardAfter()
  }
}

class MainViewModel(private val contentService: ContentService) : ViewModel(),
  MainContract.MainViewModelContract {

  val textVersion = ObservableField("")

  private lateinit var view: MainViewContract

  // --
  override fun init(view: MainViewContract) {
    this.view = view
    this.view.initMainView()
  }

  override fun start() {
    if (!::view.isInitialized) return
    initDefault()
    this.view.delayToDashboardAfter()
  }

  // --
  private fun initDefault() {
    textVersion.set(contentService.getAppVersion().getVersionText())
  }
}