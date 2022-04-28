package id.kputro.dragon.ui.dashboard

import androidx.lifecycle.ViewModel
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.entity.SpaceSize.ACTION_BAR_SIZE
import id.kputro.dragon.entity.SpaceSize.M
import id.kputro.dragon.entity.SpaceSize.XXL
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.dashboard.DashboardContract.DashboardViewContract

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
    this.view.showLoading(true)
  }

  // --
  private fun initMenu() {
    val mMenu = ArrayList<MenuItemModel>()
    mMenu.add(MenuItemModel.space(ACTION_BAR_SIZE))
    mMenu.add(MenuItemModel.header("Hello", "Kevin Putro"))
    this.view.setMenu(mMenu)
  }
}