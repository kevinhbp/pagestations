package id.kputro.dragon.ui.menu

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.extension.MenuType
import id.kputro.dragon.R.layout
import id.kputro.dragon.ui.base.BaseAdapter
import id.kputro.dragon.ui.base.BaseAdapterListener.AppLinkListener
import id.kputro.dragon.ui.base.BaseViewHolder
import id.kputro.dragon.ui.menu.header.MenuHeaderViewHolder
import id.kputro.dragon.ui.menu.item.MenuItemViewHolder
import id.kputro.dragon.ui.menu.space.MenuSpaceViewHolder

class MenuAdapter : BaseAdapter<MenuItemModel, AppLinkListener>() {

  override fun getItemViewType(position: Int): Int {
    return models[position].type
  }

  override fun getLayoutResId(viewType: Int): Int {
    return when (viewType) {
      MenuType.HEADER -> layout.menu_item_header
      MenuType.ITEM -> layout.menu_item_default
      MenuType.FOOTER -> layout.menu_item_footer
      else -> layout.menu_item_space
    }
  }

  override fun getViewHolder(
    viewType: Int,
    view: ViewDataBinding
  ): BaseViewHolder<MenuItemModel, AppLinkListener> {
    return when (viewType) {
      MenuType.HEADER -> MenuHeaderViewHolder(view)
      MenuType.ITEM -> MenuItemViewHolder(view)
      MenuType.FOOTER -> MenuHeaderViewHolder(view)
      else -> MenuSpaceViewHolder(view)
    }
  }

  override fun onBindViewHolder(
    holder: BaseViewHolder<MenuItemModel, AppLinkListener>,
    position: Int
  ) {
    val model = models[position].apply {
      this.position = position
      this.size = itemCount
    }

    when (holder) {
      is MenuHeaderViewHolder -> holder.bind(model, default)
      is MenuSpaceViewHolder -> holder.bind(model, default)
      is MenuItemViewHolder -> holder.bind(model, default)
    }
  }

  // --
  private val default = object : AppLinkListener {
    override fun onRequestedAppLink(target: String) {
      listener?.onRequestedAppLink(target)
    }
  }

  // --
  fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
    return object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (getItemViewType(position) == MenuType.ITEM) {
          1
        } else {
          2
        }
      }
    }
  }

  fun setRecyclerView(mRv: RecyclerView, mLm: GridLayoutManager, mListener: AppLinkListener) {
    mRv.apply {
      isNestedScrollingEnabled = false
      setHasFixedSize(true)
      setItemViewCacheSize(10)

      adapter = this@MenuAdapter
      layoutManager = mLm
    }

    this.listener = mListener
  }
}