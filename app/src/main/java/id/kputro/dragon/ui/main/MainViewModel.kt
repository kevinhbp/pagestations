package id.kputro.dragon.ui.main

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import id.kputro.dragon.core.datastore.users.UserDatastore
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.main.MainContract.MainViewContract

interface MainContract {

  interface MainViewModelContract : BaseViewModel<MainViewContract> {

  }

  interface MainViewContract : BaseView {
    fun initMainView()
    fun delayToDashboardAfter()
  }
}

class MainViewModel(
  private val contentService: ContentService,
  private val userDatastore: UserDatastore
) : ViewModel(),
  MainContract.MainViewModelContract {

  val textVersion = ObservableField("")

  private lateinit var view: MainViewContract

  // --
  override fun init(view: MainViewContract, data: Intent?) {
    this.view = view
    this.view.initMainView()
  }

  override fun start() {
    if (!::view.isInitialized) return
    initDefault()
    goToDashboard()
  }

  // --
  private fun initDefault() {
    textVersion.set(contentService.getAppVersion().getVersionText())
  }

  private fun goToDashboard() {
    this.view.delayToDashboardAfter()
  }
}