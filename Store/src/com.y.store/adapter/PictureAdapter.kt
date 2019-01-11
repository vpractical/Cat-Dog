package com.y.store.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.y.store.extend.urlImg
import kotlinx.android.synthetic.main.store_item_picture.view.*


class PictureAdapter(private val activity: Activity, itemLayout: Int, data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(itemLayout, data) {

    fun setOnItemClickListener(listener: ((pos: Int) -> Unit)) {
        itemClickListener = listener
    }

    private var itemClickListener: ((pos: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder?, item: String?) {
        with(helper!!.itemView) {
            ivStoreItemPicture.urlImg(activity, item)
            setOnClickListener { itemClickListener?.invoke(helper.layoutPosition) }
        }

    }

}
