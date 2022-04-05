package id.kputro.dragon.ui.components.actionbar

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.dragon.core.extension.isFilled
import id.kputro.dragon.entity.ActionBarBuilder
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.R.drawable
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.components.actionbar.ActionBarContract.ActionBarViewContract

interface ActionBarContract {
  interface ActionBarViewModelContract : BaseViewModel<ActionBarViewContract> {
    fun onClickButtonBack(v: View?)
    fun onClickButtonA(v: View?)
    fun onClickButtonB(v: View?)
  }

  interface ActionBarViewContract : BaseView {
    fun getActionBarBuilder(): ActionBarBuilder
  }
}

class ActionBarViewModel : ViewModel(),
  ActionBarContract.ActionBarViewModelContract {

  val imageButtonBack = ObservableInt()
  val imageButtonA = ObservableInt()
  val imageButtonB = ObservableInt()

  val textTitle = ObservableField("")
  val textSubtitle = ObservableField("")
  val textButtonA = ObservableField("")
  val textButtonB = ObservableField("")

  val visibilityButtonBack = ObservableInt(View.GONE)

  val alphaBackground = ObservableFloat(0f)

  private lateinit var view: ActionBarViewContract
  private lateinit var builder: ActionBarBuilder

  override fun init(view: ActionBarViewContract) {
    this.view = view
    this.builder = this.view.getActionBarBuilder()
  }

  override fun start() {
    if (!::builder.isInitialized) return
    setText(builder)
    setButtonBack(builder)
    setButtonA(builder)
    setButtonB(builder)
  }

  // --
  private fun setText(builder: ActionBarBuilder) {
    val mText = builder.text ?: return
    this.textTitle.set(mText.title)
    this.textSubtitle.set(mText.subtitle)
  }

  private fun setButtonBack(builder: ActionBarBuilder) {
    visibilityButtonBack.set(View.GONE)
    imageButtonBack.set(drawable.ic_arrow_left)
    val mButton = builder.buttonBack ?: return
    if (mButton.imageDrawable != null) {
      imageButtonBack.set(mButton.imageDrawable)
    }
    visibilityButtonBack.set(View.VISIBLE)
  }

  private fun setButtonA(builder: ActionBarBuilder) {
    val mButtonA = builder.buttonA ?: return
    if (mButtonA.title.isFilled()) {
      textButtonA.set(mButtonA.title)
    }
    if (mButtonA.imageDrawable != null) {
      imageButtonA.set(mButtonA.imageDrawable)
    }
  }

  private fun setButtonB(builder: ActionBarBuilder) {
    val mButtonB = builder.buttonB ?: return
    if (mButtonB.title.isFilled()) {
      textButtonB.set(mButtonB.title)
    }
    if (mButtonB.imageDrawable != null) {
      imageButtonB.set(mButtonB.imageDrawable)
    }
  }

  // --
  override fun onClickButtonBack(v: View?) {
    if (!::builder.isInitialized) return
    builder.buttonBack?.callback?.invoke()
  }

  override fun onClickButtonA(v: View?) {
    if (!::builder.isInitialized) return
    builder.buttonA?.callback?.invoke()
  }

  override fun onClickButtonB(v: View?) {
    if (!::builder.isInitialized) return
    builder.buttonB?.callback?.invoke()
  }
}