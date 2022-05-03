package id.kputro.dragon.ui.menu.space

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.entity.SpaceSize.S
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.space.MenuSpaceContract.MenuSpaceViewContract

interface MenuSpaceContract {
  interface MenuSpaceViewModelContract : BaseViewModel<MenuSpaceViewContract> {
    fun setModel(mModel: MenuItemModel)
  }

  interface MenuSpaceViewContract {
    fun initMenuSpaceView()
  }
}

class MenuSpaceViewModel : ViewModel(),
  MenuSpaceContract.MenuSpaceViewModelContract {

  private lateinit var view: MenuSpaceViewContract

  val model = ObservableField<MenuItemModel>()

  override fun init(view: MenuSpaceViewContract, data: Intent?) {
    this.view = view
  }

  override fun start() {
    setModel(MenuItemModel.space(S))
  }

  override fun setModel(mModel: MenuItemModel) {
    model.set(mModel)
  }
}