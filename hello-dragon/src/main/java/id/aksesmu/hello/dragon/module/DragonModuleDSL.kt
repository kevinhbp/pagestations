package id.aksesmu.hello.dragon.module

typealias ModuleDeclaration = DragonModule.() -> Unit

fun dragonModule(moduleDeclaration: ModuleDeclaration): DragonModule {
  val module = DragonModule()
  moduleDeclaration(module)
  return module
}