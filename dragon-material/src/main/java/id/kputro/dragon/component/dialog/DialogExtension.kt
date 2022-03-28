package id.kputro.dragon.component.dialog

import id.kputro.dragon.DragonApplication

typealias DialogReferenceDeclaration = DragonDialog.() -> Unit

fun setupDialogReference(declaration: DialogReferenceDeclaration) {
  val mDialog = DragonApplication.get().mDialog
  declaration.invoke(mDialog)
}