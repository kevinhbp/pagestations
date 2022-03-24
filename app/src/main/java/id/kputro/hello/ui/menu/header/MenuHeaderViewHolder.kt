package id.kputro.hello.ui.menu.header

import androidx.databinding.ViewDataBinding
import id.kputro.hello.databinding.MenuItemHeaderBinding
import id.kputro.hello.entity.MenuItemModel
import id.kputro.hello.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.hello.ui.base.BaseViewHolder

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