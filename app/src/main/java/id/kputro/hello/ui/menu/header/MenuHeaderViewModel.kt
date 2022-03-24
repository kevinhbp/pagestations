package id.kputro.hello.ui.menu.header

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.hello.entity.MenuItemModel
import id.kputro.hello.ui.base.BaseViewModel
import id.kputro.hello.ui.menu.header.MenuHeaderContract.MenuHeaderViewContract

interface MenuHeaderContract {
  interface MenuHeaderViewModelContract : BaseViewModel<MenuHeaderViewContract> {
    fun setModel(mModel: MenuItemModel)
  }

  interface MenuHeaderViewContract {
    fun initMenuHeaderView()
  }
}

class MenuHeaderViewModel : ViewModel(),
  MenuHeaderContract.MenuHeaderViewModelContract {

  private lateinit var view: MenuHeaderViewContract

  val imageBackground = ObservableInt()
  val imagePhoto = ObservableInt()
  val textTitle = ObservableField("")
  val textSubtitle = ObservableField("")

  override fun init(view: MenuHeaderViewContract) {
    this.view = view
  }

  override fun start() { /* not used at the moment.. */ }

  override fun setModel(mModel: MenuItemModel) {
    if (mModel.imageResId != null) {
      imagePhoto.set(mModel.imageResId ?: 0)
    }
    mModel.run {
      if (imageResId != null) imagePhoto.set(imageResId!!)
      if (backgroundResId != null) imageBackground.set(backgroundResId!!)
      textTitle.set(name)
      textSubtitle.set(descriptions)
    }
  }
}