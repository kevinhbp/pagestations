package id.kputro.hello.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<M, N> : RecyclerView.Adapter<BaseViewHolder<M, N>>() {

  val models : ArrayList<M> = arrayListOf()

  var listener : N? = null

  abstract fun getViewHolder(viewType: Int, view: ViewDataBinding): BaseViewHolder<M, N>

  abstract fun getLayoutResId(viewType: Int): Int

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M, N> {
    val mContext = parent.context
    val mLayoutInflater = LayoutInflater.from(mContext)
    val mBinding = DataBindingUtil.inflate<ViewDataBinding>(
      mLayoutInflater,
      getLayoutResId(viewType),
      parent,
      false
    )
    return getViewHolder(viewType, mBinding)
  }

  override fun getItemCount(): Int {
    return models.size
  }
}