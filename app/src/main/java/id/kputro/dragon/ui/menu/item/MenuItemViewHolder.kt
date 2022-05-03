package id.kputro.dragon.ui.menu.item

import androidx.databinding.ViewDataBinding
import id.kputro.dragon.databinding.MenuItemDefaultBinding
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.dragon.ui.base.BaseViewHolder

class MenuItemViewHolder(view: ViewDataBinding) :
  BaseViewHolder<MenuItemModel, AppLinkListener>(view),
  MenuItemContract.MenuItemViewContract {

  private lateinit var mViewModel: MenuItemViewModel
  private lateinit var mBinding: MenuItemDefaultBinding
  private lateinit var mModel: MenuItemModel
  private lateinit var mListener: AppLinkListener

  override fun bind(mModel: MenuItemModel, mListener: AppLinkListener) {
    this.mModel = mModel
    this.mListener = mListener
    this.mBinding = getBinding()
    this.mViewModel = MenuItemViewModel()

    this.mBinding.menuItemViewModel = this.mViewModel
    this.mBinding.executePendingBindings()

    this.mViewModel.init(this)
    this.mViewModel.start()

    this.mViewModel.setModel(mModel)
  }

  // --
  override fun goTo(target: String) {
    if (!::mListener.isInitialized) return
    mListener.onRequestedAppLink(target)
  }
}