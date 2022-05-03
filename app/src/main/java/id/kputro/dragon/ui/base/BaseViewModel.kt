package id.kputro.dragon.ui.base

import android.content.Intent

interface BaseViewModel<T> {
  fun init(view: T, data: Intent? = null)
  fun start()
}