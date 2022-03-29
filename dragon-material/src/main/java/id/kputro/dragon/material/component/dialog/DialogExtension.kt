package id.kputro.dragon.material.component.dialog

import id.kputro.dragon.material.DragonApplication


typealias DialogReferenceDeclaration = DragonDialog.() -> Unit

fun setupDialogReference(declaration: DialogReferenceDeclaration) {
  val mDialog = DragonApplication.get().mDialog
  declaration.invoke(mDialog)
}

fun showMessageDialog(title: String, message: String, onDismiss: (() -> Unit)?) {
  val mDialog = DragonApplication.get().mDialog
  mDialog.showMessage(title, message, onDismiss)
}