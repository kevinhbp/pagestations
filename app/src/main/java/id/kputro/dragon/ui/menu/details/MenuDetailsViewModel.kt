package id.kputro.dragon.ui.menu.details

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.dragon.core.extension.replaceIfNull
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.singleton.DataSingleton
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.details.MenuDetailsContract.MenuDetailsViewContract
import id.kputro.dragon.utils.applink.getClosePageAddrs

interface MenuDetailsContract {
  interface MenuDetailsViewModelContract : BaseViewModel<MenuDetailsViewContract> {
    fun onClickClose(v: View?)
    fun onClickOpen(v: View?)
  }

  interface MenuDetailsViewContract : BaseView {
    fun initMenuDetailsView()
    fun setExpandSheet(flag: Boolean)
  }
}

class MenuDetailsViewModel() : ViewModel(),
  MenuDetailsContract.MenuDetailsViewModelContract {

  private lateinit var view: MenuDetailsViewContract
  private var model: MenuItemModel? = null

  val textName = ObservableField("")
  val textDescription = ObservableField("")

  val imagePhoto = ObservableInt(0)
  val imageBackground = ObservableInt(0)

  var flagExpanded = false

  // --
  override fun init(view: MenuDetailsViewContract, data: Intent?) {
    this.view = view
    this.view.initMenuDetailsView()
  }

  override fun start() {
    if (!::view.isInitialized) return
    model = DataSingleton.get().selMenuItem
    if (model == null) return

    textName.set(model!!.name.replaceIfNull())
    textDescription.set(model!!.descriptions.replaceIfNull())
    if (model?.imageResId != null) {
      imagePhoto.set(model!!.imageResId ?: 0)
    }
    if (model?.backgroundResId != null) {
      imageBackground.set(model!!.backgroundResId ?: 0)
    }

    view.setExpandSheet(true)
  }

  // --
  override fun onClickClose(v: View?) {
    if (!::view.isInitialized) return
    view.goTo(getClosePageAddrs())
  }

  override fun onClickOpen(v: View?) {
    if (!::view.isInitialized) return
    val mModel = model ?: return
    view.goTo(mModel.applink)
  }
}