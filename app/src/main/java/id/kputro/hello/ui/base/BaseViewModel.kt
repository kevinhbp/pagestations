package id.kputro.hello.ui.base

interface BaseViewModel<in T : BaseView> {
  fun init(view: T)
  fun start()
}