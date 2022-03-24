package id.aksesmu.hello.dragon.module

typealias ModuleDeclaration = DragonModule.() -> Unit

fun dragonModule(createdAtStart: Boolean = false, moduleDeclaration: ModuleDeclaration): DragonModule {
  val module = DragonModule(createdAtStart)
  moduleDeclaration(module)
  return module
}