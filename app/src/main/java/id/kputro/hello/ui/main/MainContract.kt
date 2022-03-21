package id.kputro.hello.ui.main

import id.kputro.hello.ui.base.BaseView
import id.kputro.hello.ui.base.BaseViewModel

interface MainContract {

  interface MainViewModelContract : BaseViewModel<MainViewContract> {

  }

  interface MainViewContract : BaseView {
    fun initMainView()
    fun delayToDashboardAfter()
  }
}