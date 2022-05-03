package id.kputro.dragon.ui.menu.details

import android.content.Intent
import androidx.lifecycle.ViewModel
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.menu.details.MenuDetailsContract.MenuDetailsViewContract

interface MenuDetailsContract {
  interface MenuDetailsViewModelContract : BaseViewModel<MenuDetailsViewContract> {
  }

  interface MenuDetailsViewContract : BaseView {

  }
}

class MenuDetailsViewModel() : ViewModel(),
  MenuDetailsContract.MenuDetailsViewModelContract {
  private lateinit var view: MenuDetailsViewContract

  // --
  override fun init(view: MenuDetailsViewContract, data: Intent?) {

  }

  override fun start() {

  }

  // --
}