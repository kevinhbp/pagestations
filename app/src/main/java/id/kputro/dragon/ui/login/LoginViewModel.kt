package id.kputro.dragon.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.login.LoginContract.LoginViewContract

interface LoginContract {
  interface LoginViewModelContract : BaseViewModel<LoginViewContract> {

  }

  interface LoginViewContract : BaseView {
    fun initLoginView()
  }
}

class LoginViewModel(private val contentService: ContentService) : ViewModel(),
  LoginContract.LoginViewModelContract {

  private lateinit var view: LoginViewContract

  override fun init(view: LoginViewContract, data: Intent?) {
    this.view = view
    this.view.initLoginView()
  }

  override fun start() {
    // todo
  }

  // --

}