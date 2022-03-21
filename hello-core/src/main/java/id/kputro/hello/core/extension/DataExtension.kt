package id.kputro.hello.core.extension

fun String?.isFilled(): Boolean {
  if (this == null) return false
  return (this.isNotEmpty())
}
fun String?.replaceIfNull(mReplaceWith: String = ""): String {
  return this ?: mReplaceWith
}