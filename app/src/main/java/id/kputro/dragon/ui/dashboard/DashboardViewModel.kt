package id.kputro.dragon.ui.dashboard

import androidx.lifecycle.ViewModel
import id.kputro.dragon.R.drawable
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.entity.SpaceSize.ACTION_BAR_SIZE
import id.kputro.dragon.entity.SpaceSize.S
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
  }

  // --
  private fun initMenu() {
    val mMenu = ArrayList<MenuItemModel>()
    mMenu.add(MenuItemModel.space(ACTION_BAR_SIZE))
    mMenu.add(
      MenuItemModel.header(
        mTitle = "Hello,",
        mSubtitle = "Kevin Putro",
        mPhotoResId = drawable.fo_illustration_user_2
      )
    )
    mMenu.add(MenuItemModel.space(S))
    mMenu.add(
      MenuItemModel.menu(
        "",
        "Applink Saver",
        "For You to increase convenience and productivity.",
        drawable.fo_bg_dark_navy,
        drawable.fo_ic_database
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        "",
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.fo_bg_light_orange,
        drawable.fo_ic_crystal_ball
      )
    )
    this.view.setMenu(mMenu)
  }
}