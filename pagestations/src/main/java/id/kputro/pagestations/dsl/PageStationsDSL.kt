package id.kputro.pagestations.dsl

import android.content.Context
import id.kputro.pagestations.module.PageStationsModule

typealias PageStationsModuleDeclaration = PageStationsModule.() -> Unit

fun startPageStationsModule(moduleDeclaration: PageStationsModuleDeclaration): PageStationsModule {
  val module = PageStationsModule.init()
  moduleDeclaration(module)
  return module
}

fun Context.navigateTo(target: String) {
  PageStationsModule.get().route(this, target)
}