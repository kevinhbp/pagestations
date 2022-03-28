package id.kputro.dragon.ui.base

interface BaseView {

  fun showLoading(show: Boolean)

  fun showMessage(title: String?, message: String, onDismiss: (() -> Unit)?)

  fun showToast(message: String, doLong: Boolean = false)

  fun goTo(target: String)
}