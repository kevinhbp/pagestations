package id.kputro.dragon.extension

import androidx.annotation.IntDef
import kotlin.annotation.AnnotationRetention.SOURCE

@IntDef(MenuType.SPACE, MenuType.HEADER, MenuType.ITEM, MenuType.FOOTER)
@Retention(SOURCE)
annotation class MenuType {
  companion object {
    const val SPACE = 0
    const val HEADER = 1
    const val ITEM = 2
    const val FOOTER = 3
  }
}