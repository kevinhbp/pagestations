package id.kputro.dragon.entity

import android.view.View
import androidx.annotation.DrawableRes
import id.kputro.dragon.R.drawable
import id.kputro.dragon.entity.SpaceSize.ACTION_BAR_SIZE
import id.kputro.dragon.entity.SpaceSize.L
import id.kputro.dragon.entity.SpaceSize.M
import id.kputro.dragon.entity.SpaceSize.S
import id.kputro.dragon.entity.SpaceSize.XL
import id.kputro.dragon.entity.SpaceSize.XXL
import id.kputro.dragon.extension.MenuType
import java.io.Serializable

data class MenuItemModel(
  @MenuType val type: Int
) : BaseItemModel(), Serializable {
  var applink: String = ""
  var name: String = ""
  var descriptions: String = ""

  @DrawableRes
  var imageResId: Int? = null

  @DrawableRes
  var backgroundResId: Int? = null

  var spaceSize: SpaceSize = S

  companion object {
    fun space(mSpaceSize: SpaceSize): MenuItemModel {
      return MenuItemModel(MenuType.SPACE).apply {
        spaceSize = mSpaceSize
      }
    }

    fun header(
      mTitle: String,
      mSubtitle: String,
      mBgResId: Int = drawable.bg_standard_01,
      mPhotoResId: Int = drawable.ic_avatar
    ): MenuItemModel {
      return MenuItemModel(MenuType.HEADER).apply {
        name = mTitle
        descriptions = mSubtitle
        backgroundResId = mBgResId
        imageResId = mPhotoResId
      }
    }

    fun menu(
      mApplink: String,
      mTitle: String,
      mSubtitle: String,
      mBgResId: Int?,
      mIconResId: Int?): MenuItemModel {
      return MenuItemModel(MenuType.ITEM).apply {
        name = mTitle
        descriptions = mSubtitle
        backgroundResId = mBgResId
        imageResId = mIconResId
        applink = mApplink
      }
    }
  }

  // region Space
  fun getVisibilitySpaceABS(): Int {
    return if (spaceSize == ACTION_BAR_SIZE) View.VISIBLE else View.GONE
  }

  fun getVisibilitySpaceXXL(): Int {
    return if (spaceSize == XXL) View.VISIBLE else View.GONE
  }

  fun getVisibilitySpaceXL(): Int {
    return if (spaceSize == XL) View.VISIBLE else View.GONE
  }

  fun getVisibilitySpaceL(): Int {
    return if (spaceSize == L) View.VISIBLE else View.GONE
  }

  fun getVisibilitySpaceM(): Int {
    return if (spaceSize == M) View.VISIBLE else View.GONE
  }

  fun getVisibilitySpaceS(): Int {
    return if (spaceSize == S) View.VISIBLE else View.GONE
  }
  // endregion
}

