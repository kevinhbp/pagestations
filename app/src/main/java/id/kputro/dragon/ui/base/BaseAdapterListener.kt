package id.kputro.dragon.ui.base

interface BaseAdapterListener {

  interface AppLinkListener {
    fun onRequestedAppLink(target: String)
  }
}