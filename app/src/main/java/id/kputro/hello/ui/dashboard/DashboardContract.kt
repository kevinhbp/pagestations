package id.kputro.hello.ui.dashboard

import id.kputro.hello.entity.DashboardMenu
import id.kputro.hello.ui.base.BaseView
import id.kputro.hello.ui.base.BaseViewModel

interface DashboardContract {
  interface DashboardViewModelContract : BaseViewModel<DashboardViewContract> {
    
  }

  interface DashboardViewContract : BaseView {
    fun initDashboardView()
    fun setMenu(data: ArrayList<DashboardMenu>)
  }
}