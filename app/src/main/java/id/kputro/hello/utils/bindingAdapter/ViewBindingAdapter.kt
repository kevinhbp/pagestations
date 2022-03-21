package id.kputro.hello.utils.bindingAdapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

  @BindingAdapter("setImage")
  @JvmStatic
  fun setImage(
    view: ImageView,
    @DrawableRes resId: Int?
  ) {
    if (resId == null) return
    if (resId == 0) return
    view.setImageResource(resId)
  }
}