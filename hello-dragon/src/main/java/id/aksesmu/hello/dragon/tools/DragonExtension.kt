package id.aksesmu.hello.dragon.tools

import android.content.Context
import id.aksesmu.hello.dragon.DragonAppDeclaration
import id.aksesmu.hello.dragon.DragonApplication
import id.aksesmu.hello.dragon.dragonApplication

fun startDragonRouter(appDeclaration: DragonAppDeclaration): DragonApplication =
  dragonApplication(appDeclaration)

fun Context.navigateTo(target: String) {
  DragonApplication.get().route(this, target)
}