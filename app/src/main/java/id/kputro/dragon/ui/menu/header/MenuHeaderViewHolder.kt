package id.kputro.dragon.ui.menu.header

import androidx.databinding.ViewDataBinding
import id.kputro.dragon.databinding.MenuItemHeaderBinding
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.dragon.ui.base.BaseViewHolder

class MenuHeaderViewHolder(view: ViewDataBinding) :
  BaseViewHolder<MenuItemModel, AppLinkListener>(view),
  MenuHeaderContract.MenuHeaderViewContract {

  private lateinit var mViewModel: MenuHeaderViewModel
  private lateinit var mBinding: MenuItemHeaderBinding
  private lateinit var mModel: MenuItemModel

  override fun bind(mModel: MenuItemModel, mListener: AppLinkListener) {
    this.mModel = mModel
    this.mBinding = getBinding()
    this.mViewModel = MenuHeaderViewModel()

    this.mBinding.menuHeaderViewModel = this.mViewModel
    this.mBinding.executePendingBindings()

    this.mViewModel.init(this)
    this.mViewModel.start()

    this.mViewModel.setModel(mModel)
  }

  // --
  override fun initMenuHeaderView() {
    // not used at the moment..
  }
}