package id.kputro.hello.ui.menu.space

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.hello.entity.MenuItemModel
import id.kputro.hello.entity.SpaceSize.L
import id.kputro.hello.entity.SpaceSize.M
import id.kputro.hello.entity.SpaceSize.S
import id.kputro.hello.entity.SpaceSize.XL
import id.kputro.hello.entity.SpaceSize.XXL
import id.kputro.hello.ui.base.BaseViewModel
import id.kputro.hello.ui.menu.space.MenuSpaceContract.MenuSpaceViewContract

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

  val visibilitySpaceS = ObservableInt(View.GONE)
  val visibilitySpaceM = ObservableInt(View.GONE)
  val visibilitySpaceL = ObservableInt(View.GONE)
  val visibilitySpaceXL = ObservableInt(View.GONE)
  val visibilitySpaceXXL = ObservableInt(View.GONE)

  override fun init(view: MenuSpaceViewContract) {
    this.view = view
  }

  override fun start() { /* not used at the moment.. */
  }

  override fun setModel(mModel: MenuItemModel) {
    visibilitySpaceS.set(if (mModel.spaceSize == S) View.VISIBLE else View.GONE)
    visibilitySpaceM.set(if (mModel.spaceSize == M) View.VISIBLE else View.GONE)
    visibilitySpaceL.set(if (mModel.spaceSize == L) View.VISIBLE else View.GONE)
    visibilitySpaceXL.set(if (mModel.spaceSize == XL) View.VISIBLE else View.GONE)
    visibilitySpaceXXL.set(if (mModel.spaceSize == XXL) View.VISIBLE else View.GONE)
  }
}