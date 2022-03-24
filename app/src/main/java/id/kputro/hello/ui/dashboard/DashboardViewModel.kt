package id.kputro.hello.ui.dashboard

import androidx.lifecycle.ViewModel
import id.kputro.hello.R.drawable
import id.kputro.hello.dimodule.ContentService
import id.kputro.hello.entity.MenuItemModel
import id.kputro.hello.entity.SpaceSize.L
import id.kputro.hello.entity.SpaceSize.M
import id.kputro.hello.extension.MenuType
import id.kputro.hello.ui.base.BaseView
import id.kputro.hello.ui.base.BaseViewModel
import id.kputro.hello.ui.dashboard.DashboardContract.DashboardViewContract

interface DashboardContract {
  interface DashboardViewModelContract : BaseViewModel<DashboardViewContract> {

  }

  interface DashboardViewContract : BaseView {
    fun initDashboardView()
    fun setMenu(data: ArrayList<MenuItemModel>)
  }
}

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
    val mMenu = ArrayList<MenuItemModel>()
    mMenu.add(MenuItemModel.space(M))
    mMenu.add(MenuItemModel.header("Hello", "Kevin Putro"))
    this.view.setMenu(mMenu)
  }
}