package id.kputro.hello.entity

import androidx.annotation.DrawableRes
import id.kputro.hello.R.drawable
import id.kputro.hello.extension.MenuType
import java.io.Serializable

data class MenuItemModel(
  @MenuType val type: Int
) : BaseItemModel(), Serializable {
  var applink: String = ""
  var name: String = ""
  var descriptions: String = ""
  var imageUrl: String? = null

  @DrawableRes
  var imageResId: Int? = null

  @DrawableRes
  var backgroundResId: Int? = null

  var spaceSize: SpaceSize = SpaceSize.S

  companion object {
    fun space(mSpaceSize: SpaceSize) : MenuItemModel {
      return MenuItemModel(MenuType.SPACE).apply {
        spaceSize = mSpaceSize
      }
    }

    fun header(mTitle: String, mSubtitle: String, mBgResId: Int = drawable.bg_illustration_01, mPhotoResId: Int = drawable.ic_avatar) : MenuItemModel {
      return MenuItemModel(MenuType.HEADER).apply {
        name = mTitle
        descriptions = mSubtitle
        backgroundResId = mBgResId
        imageResId = mPhotoResId
      }
    }
  }
}

