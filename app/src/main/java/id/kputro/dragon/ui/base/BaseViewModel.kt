package id.kputro.dragon.ui.base

interface BaseViewModel<T> {
  fun init(view: T)
  fun start()
}