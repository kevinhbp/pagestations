package id.kputro.hello.ui.dashboard

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.hello.R
import id.kputro.hello.dimodule.ContentService
import id.kputro.hello.ui.dashboard.DashboardContract.DashboardViewContract

class DashboardViewModel(private val contentService: ContentService) : ViewModel(),
  DashboardContract.DashboardViewModelContract {

  private lateinit var view: DashboardViewContract

  // --
  override fun init(view: DashboardViewContract) {
    this.view = view
    this.view.initDashboardView()
  }

  override fun start() {
    initMenu()
  }

  // --
  private fun initMenu() {

  }
}