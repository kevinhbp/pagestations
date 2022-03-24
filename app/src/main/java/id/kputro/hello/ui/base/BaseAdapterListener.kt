package id.kputro.hello.ui.base

interface BaseAdapterListener {

  interface AppLinkListener {
    fun onRequestedAppLink(target: String)
  }
}