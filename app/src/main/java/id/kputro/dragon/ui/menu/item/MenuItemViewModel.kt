package id.kputro.dragon.ui.menu.item

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.item.MenuItemContract.MenuItemViewContract

interface MenuItemContract {
  interface MenuItemViewModelContract : BaseViewModel<MenuItemViewContract> {
    fun setModel(mModel: MenuItemModel)
  }

  interface MenuItemViewContract {
    fun initMenuItemView()
  }
}

class MenuItemViewModel : ViewModel(),
  MenuItemContract.MenuItemViewModelContract {

  private lateinit var view: MenuItemViewContract

  val imageBackground = ObservableInt()
  val imageIcon = ObservableInt()
  val textTitle = ObservableField("")
  val textSubtitle = ObservableField("")

  override fun init(view: MenuItemViewContract) {
    this.view = view
  }

  override fun start() { /* not used at the moment.. */ }

  override fun setModel(mModel: MenuItemModel) {
    mModel.run {
      if (imageResId != null) imageIcon.set(mModel.imageResId!!)
      if (backgroundResId != null) imageBackground.set(backgroundResId!!)
      textTitle.set(name)
      textSubtitle.set(descriptions)
    }
  }
}