package id.kputro.dragon.ui.menu.header

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.header.MenuHeaderContract.MenuHeaderViewContract

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

  override fun init(view: MenuHeaderViewContract, data: Intent?) {
    this.view = view
  }

  override fun start() { /* not used at the moment.. */
  }

  override fun setModel(mModel: MenuItemModel) {
    mModel.run {
      if (imageResId != null) imagePhoto.set(imageResId!!)
      if (backgroundResId != null) imageBackground.set(backgroundResId!!)
      textTitle.set(name)
      textSubtitle.set(descriptions)
    }
  }
}