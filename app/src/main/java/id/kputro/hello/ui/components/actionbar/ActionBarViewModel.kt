package id.kputro.hello.ui.components.actionbar

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import id.kputro.hello.core.extension.isFilled
import id.kputro.hello.entity.ActionBarBuilder
import id.kputro.hello.ui.base.BaseView
import id.kputro.hello.ui.base.BaseViewModel
import id.kputro.hello.ui.components.actionbar.ActionBarContract.ActionBarViewContract
import id.kputro.hello.utils.const.Global

interface ActionBarContract {
  interface ActionBarViewModelContract : BaseViewModel<ActionBarViewContract> {
    fun onClickLogo(v: View?)
    fun onClickText(v: View?)
    fun onClickButtonA(v: View?)
    fun onClickButtonB(v: View?)
  }

  interface ActionBarViewContract : BaseView {
    fun getActionBarBuilder(): ActionBarBuilder
  }
}

class ActionBarViewModel : ViewModel(),
  ActionBarContract.ActionBarViewModelContract {

  val imageLogo = ObservableInt()
  val imageButtonA = ObservableInt()
  val imageButtonB = ObservableInt()

  val textTitle = ObservableField("")
  val textSubtitle = ObservableField("")
  val textButtonA = ObservableField("")
  val textButtonB = ObservableField("")

  private lateinit var view: ActionBarViewContract
  private lateinit var builder: ActionBarBuilder

  override fun init(view: ActionBarViewContract) {
    this.view = view
    this.builder = this.view.getActionBarBuilder()
  }

  override fun start() {
    if (!::builder.isInitialized) return
    setText(builder)
    setLogo(builder)
    setButtonA(builder)
    setButtonB(builder)
  }

  // --
  private fun setText(builder: ActionBarBuilder) {
    val mText = builder.text ?: return
    this.textTitle.set(mText.title)
    this.textSubtitle.set(mText.subtitle)
  }

  private fun setLogo(builder: ActionBarBuilder) {
    val mLogo = builder.logo ?: return
    val mImage = mLogo.imageDrawable ?: return
    this.imageLogo.set(mImage)
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
  override fun onClickLogo(v: View?) {
    if (!::builder.isInitialized) return
    builder.logo?.callback?.invoke()
  }

  override fun onClickText(v: View?) {
    if (!::builder.isInitialized) return
    builder.text?.callback?.invoke()
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