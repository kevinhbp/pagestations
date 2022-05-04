package id.kputro.dragon.ui.menu.details

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.kputro.dragon.R.layout
import id.kputro.dragon.databinding.MenuDetailsActivityBinding
import id.kputro.dragon.extension.ViewExtension
import id.kputro.dragon.extension.doDebounceCall
import id.kputro.dragon.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuDetailsActivity : BaseActivity<MenuDetailsActivityBinding>(layout.menu_details_activity),
  MenuDetailsContract.MenuDetailsViewContract {

  private lateinit var binding: MenuDetailsActivityBinding
  private lateinit var bottomContentBehavior: BottomSheetBehavior<ConstraintLayout>

  private val menuDetailsViewModel: MenuDetailsViewModel by viewModel()

  override fun initDataBinding(binding: MenuDetailsActivityBinding) {
    this.binding = binding
    this.binding.menuDetailsViewModel = this.menuDetailsViewModel
    this.binding.executePendingBindings()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    menuDetailsViewModel.init(this, intent)
  }

  override fun onCompleteDraw() {
    super.onCompleteDraw()
    menuDetailsViewModel.start()
  }

  // --
  override fun initMenuDetailsView() {
    initSheetBehavior()
  }

  private fun initSheetBehavior() {
    if (!::binding.isInitialized) return
    val bottomContent = binding.layoutBottomContent
    bottomContentBehavior = BottomSheetBehavior.from(bottomContent)
    bottomContentBehavior.addBottomSheetCallback(object :
      BottomSheetBehavior.BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
        setSlideViewOffset(slideOffset)
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_EXPANDED || newState == BottomSheetBehavior.STATE_COLLAPSED) {
          menuDetailsViewModel.flagExpanded.set(newState == BottomSheetBehavior.STATE_EXPANDED)
        }
      }
    })
    bottomContentBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
  }

  private fun setSlideViewOffset(offset: Float) {
    val loadingFrame = binding.loadingFrame
    val contentFrame = binding.contentFrame
    val shadowView = binding.vwShadow

    if (offset in 0f..0.5f) { // loading view
      val mAlpha = (1f - (offset * 2))
      loadingFrame.alpha = mAlpha
    } else if (offset > 0.5f) {
      loadingFrame.alpha = 0f
    }

    if (offset in 0.5f..1f) { // content view
      val xOffset = (offset - 0.5f)
      val mAlpha = ((xOffset) * 2)
      contentFrame.alpha = mAlpha
    } else if (offset < 0.5f) {
      contentFrame.alpha = 0f
    }

    if (offset in 0f..1f) {
      shadowView.alpha = offset
    }
  }

  // --
  override fun setExpandSheet(flag: Boolean) {
    if (!::binding.isInitialized) return
    if (!::bottomContentBehavior.isInitialized) return
    doDebounceCall(200L) {
      bottomContentBehavior.state =
        if (flag) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
    }
  }
}
