package id.kputro.dragon.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import id.kputro.dragon.R.drawable
import id.kputro.dragon.R.layout
import id.kputro.dragon.R.string
import id.kputro.dragon.databinding.DashboardActivityBinding
import id.kputro.dragon.entity.ActionBarBuilder
import id.kputro.dragon.entity.ButtonBuilder
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.extension.MenuType
import id.kputro.dragon.material.component.view.getParallaxListener
import id.kputro.dragon.ui.base.BaseActivity
import id.kputro.dragon.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.dragon.ui.components.actionbar.ActionBarContract
import id.kputro.dragon.ui.components.actionbar.ActionBarViewModel
import id.kputro.dragon.ui.menu.MenuAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<DashboardActivityBinding>(layout.dashboard_activity),
  DashboardContract.DashboardViewContract,
  ActionBarContract.ActionBarViewContract {

  companion object {
    fun newIntent(mContext: Context): Intent {
      return Intent(mContext, DashboardActivity::class.java)
    }
  }

  private lateinit var binding: DashboardActivityBinding

  private val dashboardViewModel: DashboardViewModel by viewModel()
  private val actionBarViewModel: ActionBarViewModel by viewModel()
  private val mAdapter = MenuAdapter()

  override fun initDataBinding(binding: DashboardActivityBinding) {
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
    initRecyclerView()
  }

  override fun setMenu(data: ArrayList<MenuItemModel>) {
    mAdapter.models.clear()
    data.forEachIndexed { index, menuItemModel ->
      try {
        mAdapter.models.add(menuItemModel)
        mAdapter.notifyItemInserted(index)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  override fun getActionBarBuilder(): ActionBarBuilder {
    val mAppName = getString(string.app_name)
    return ActionBarBuilder()
      .withText(ButtonBuilder(mAppName, "", null, null))
      .withButtonA(ButtonBuilder("", "", drawable.ic_settings, null))
      .withButtonB(ButtonBuilder("", "", drawable.ic_notifications, null))
  }

  // --
  private fun initRecyclerView() {
    val mRecyclerView = this.binding.subContent.rvMenuDashboard
    val mLayoutManager = GridLayoutManager(this, 2)
    mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (mAdapter.getItemViewType(position) == MenuType.ITEM) {
          1
        } else {
          2
        }
      }
    }
    mRecyclerView.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
      isNestedScrollingEnabled = false
      setHasFixedSize(true)
      setItemViewCacheSize(10)
    }
    mAdapter.listener = object : AppLinkListener {
      override fun onRequestedAppLink(target: String) {
        goTo(target)
      }
    }
    actionBarViewModel.alphaBackground.set(0f)
    /*mRecyclerView.getParallaxListener { _, alpha ->
      actionBarViewModel.alphaBackground.set(alpha)
    }*/
  }
}