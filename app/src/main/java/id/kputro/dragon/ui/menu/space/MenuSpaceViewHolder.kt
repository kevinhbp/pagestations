package id.kputro.dragon.ui.menu.space

import androidx.databinding.ViewDataBinding
import id.kputro.dragon.databinding.MenuItemSpaceBinding
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.dragon.ui.base.BaseViewHolder

class MenuSpaceViewHolder(view: ViewDataBinding) :
  BaseViewHolder<MenuItemModel, AppLinkListener>(view),
  MenuSpaceContract.MenuSpaceViewContract {

  private lateinit var mViewModel: MenuSpaceViewModel
  private lateinit var mBinding: MenuItemSpaceBinding
  private lateinit var mModel: MenuItemModel

  override fun bind(mModel: MenuItemModel, mListener: AppLinkListener) {
    this.mModel = mModel
    this.mBinding = getBinding()
    this.mViewModel = MenuSpaceViewModel()

    this.mBinding.viewModel = this.mViewModel
    this.mBinding.executePendingBindings()

    this.mViewModel.init(this)
    this.mViewModel.start()

    this.mViewModel.setModel(mModel)
  }

  // --
  override fun initMenuSpaceView() {
    // not used at the moment
  }
}