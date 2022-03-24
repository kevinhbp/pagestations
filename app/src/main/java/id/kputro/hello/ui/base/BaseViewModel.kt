package id.kputro.hello.ui.base

interface BaseViewModel<T> {
  fun init(view: T)
  fun start()
}