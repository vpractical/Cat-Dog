package com.y.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class PictureAdapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: String) {

    }
}
