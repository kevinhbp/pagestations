package id.kputro.dragon.ui.menu.item

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.item.MenuItemContract.MenuItemViewContract
import id.kputro.dragon.utils.applink.getMenuDetailsPageAddrs

interface MenuItemContract {
  interface MenuItemViewModelContract : BaseViewModel<MenuItemViewContract> {
    fun setModel(mModel: MenuItemModel)

    fun onClickMenuDetails(v: View?)
  }

  interface MenuItemViewContract {
    fun goTo(target: String)
  }
}

class MenuItemViewModel : ViewModel(),
  MenuItemContract.MenuItemViewModelContract {

  private lateinit var view: MenuItemViewContract
  private lateinit var model: MenuItemModel

  val imageBackground = ObservableInt()
  val imageIcon = ObservableInt()
  val textTitle = ObservableField("")
  val textSubtitle = ObservableField("")

  override fun init(view: MenuItemViewContract, data: Intent?) {
    this.view = view
  }

  override fun start() { /* not used at the moment.. */ }

  override fun setModel(mModel: MenuItemModel) {
    this.model = mModel
    mModel.run {
      if (imageResId != null) imageIcon.set(mModel.imageResId!!)
      if (backgroundResId != null) imageBackground.set(backgroundResId!!)
      textTitle.set(name)
      textSubtitle.set(descriptions)
    }
  }

  @Suppress("UNUSED_PARAM")
  override fun onClickMenuDetails(v: View?) {
    if (!::view.isInitialized) return
    if (!::model.isInitialized) return
    view.goTo(getMenuDetailsPageAddrs(model))
  }
}