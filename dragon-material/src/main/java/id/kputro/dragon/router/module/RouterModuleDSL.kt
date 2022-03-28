package id.kputro.dragon.router.module

typealias RouterModuleDeclaration = RouterModule.() -> Unit

fun routerModule(moduleDeclaration: RouterModuleDeclaration): RouterModule {
  val module = RouterModule()
  moduleDeclaration(module)
  return module
}