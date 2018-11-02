package com.y.mvp.activity.form

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.y.R

class FormAdapter(data: List<Cell>) : BaseMultiItemQuickAdapter<Cell, BaseViewHolder>(data) {

    init {
        addItemType(Cell.MULTI_CELL, R.layout.item_form_item)
        addItemType(Cell.MULTI_TOP, R.layout.item_form_item)
        addItemType(Cell.MULTI_LEFT, R.layout.item_form_item)
    }


    override fun convert(helper: BaseViewHolder, item: Cell) {
        val tv = helper.getView<TextView>(R.id.tv)
        tv.text = item.text
        when (helper.itemViewType) {
            Cell.MULTI_CELL -> {}
//            Cell.MULTI_TOP -> tv.setBackgroundColor(ColorUtil.random())
//            Cell.MULTI_LEFT -> tv.setBackgroundColor(ColorUtil.random())
        }
    }
}
