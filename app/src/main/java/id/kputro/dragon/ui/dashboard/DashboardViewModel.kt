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
import id.kputro.dragon.utils.applink.getApplinkPageAddrs
import id.kputro.dragon.utils.applink.getUpcomingFeatureAddrs

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
        mPhotoResId = drawable.illustration_user_2
      )
    )
    mMenu.add(MenuItemModel.space(S))
    mMenu.add(
      MenuItemModel.menu(
        getApplinkPageAddrs(),
        "Applink Saver",
        "Tools to increase convenience and productivity.",
        drawable.bg_standard_menu_dark_navy,
        drawable.ic_future_arrow
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        getUpcomingFeatureAddrs(),
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.bg_standard_menu_light_orange,
        drawable.ic_crystal_ball
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        getUpcomingFeatureAddrs(),
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.bg_standard_menu_light_orange,
        drawable.ic_crystal_ball
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        getUpcomingFeatureAddrs(),
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.bg_standard_menu_light_orange,
        drawable.ic_crystal_ball
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        getUpcomingFeatureAddrs(),
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.bg_standard_menu_light_orange,
        drawable.ic_crystal_ball
      )
    )
    mMenu.add(
      MenuItemModel.menu(
        getUpcomingFeatureAddrs(),
        "Upcoming Feature",
        "Will be available on the next update.",
        drawable.bg_standard_menu_light_orange,
        drawable.ic_crystal_ball
      )
    )
    this.view.setMenu(mMenu)
  }
}