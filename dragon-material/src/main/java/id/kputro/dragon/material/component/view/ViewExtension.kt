package id.kputro.dragon.material.component.view

import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

// region Parallax Listener
fun RecyclerView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      val maxRange = if (mMaxRange < 50f) 50f else if (mMaxRange > 1000f) 1000f else mMaxRange
      val offset = recyclerView.computeVerticalScrollOffset().toFloat()
      val slow = if (mSlowValue < 1) 1f else if (mSlowValue > 5) 5f else mSlowValue
      val maxOffset = if (offset > maxRange) maxRange else offset
      val maxRangeOptimum = (maxRange + 250f)
      val maxOffsetOptimum = if (offset > maxRangeOptimum) maxRangeOptimum else offset
      val translation = (0f - (maxOffsetOptimum / slow))
      val alpha = ((maxRange - maxOffset) / maxRange)
      mListener.invoke(translation, alpha)
      super.onScrolled(recyclerView, dx, dy)
    }
  })
}

fun NestedScrollView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.viewTreeObserver.addOnScrollChangedListener {
    val mY = this.scrollY.toFloat()
    val maxOffset = if (mY > mMaxRange) mMaxRange else mY
    val translation = (0f - (maxOffset / mSlowValue))
    val alpha = ((mMaxRange - maxOffset) / mMaxRange)
    mListener.invoke(translation, alpha)
  }
}

fun ScrollView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.viewTreeObserver.addOnScrollChangedListener {
    val mY = this.scrollY.toFloat()
    val maxOffset = if (mY > mMaxRange) mMaxRange else mY
    val translation = (0f - (maxOffset / mSlowValue))
    val alpha = ((mMaxRange - maxOffset) / mMaxRange)
    mListener.invoke(translation, alpha)
  }
}
// endregion

