package id.kputro.hello.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import id.kputro.hello.R.drawable
import id.kputro.hello.R.layout
import id.kputro.hello.R.string
import id.kputro.hello.databinding.ActivityDashboardBinding
import id.kputro.hello.entity.ActionBarBuilder
import id.kputro.hello.entity.ButtonBuilder
import id.kputro.hello.entity.DashboardMenu
import id.kputro.hello.ui.base.BaseActivity
import id.kputro.hello.ui.components.actionbar.ActionBarContract
import id.kputro.hello.ui.components.actionbar.ActionBarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(layout.dashboard_activity),
  DashboardContract.DashboardViewContract,
  ActionBarContract.ActionBarViewContract {

  companion object {
    fun newIntent(mContext: Context): Intent {
      return Intent(mContext, DashboardActivity::class.java)
    }
  }

  private lateinit var binding: ActivityDashboardBinding

  private val dashboardViewModel: DashboardViewModel by viewModel()
  private val actionBarViewModel: ActionBarViewModel by viewModel()

  override fun initDataBinding(binding: ActivityDashboardBinding) {
    this.binding = binding
    this.binding.dashboardViewModel = this.dashboardViewModel
    this.binding.actionBarViewModel = this.actionBarViewModel
    this.binding.executePendingBindings()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dashboardViewModel.init(this)
    actionBarViewModel.init(this)
  }

  override fun onCompleteDraw() {
    super.onCompleteDraw()
    dashboardViewModel.start()
    actionBarViewModel.start()
  }

  override fun initDashboardView() {
    // not used at the moment..
  }

  override fun setMenu(data: ArrayList<DashboardMenu>) {

  }

  override fun getActionBarBuilder(): ActionBarBuilder {
    val mAppName = getString(string.app_name)
    return ActionBarBuilder()
      .withText(ButtonBuilder(mAppName, "", null, null))
      .withLogo(ButtonBuilder("", "", drawable.ic_logo, null))
      .withButtonA(ButtonBuilder("", "", drawable.ic_settings, null))
      .withButtonB(ButtonBuilder("", "", drawable.ic_notifications, null))
  }
}