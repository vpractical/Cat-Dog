package com.y.mvp.activity.form

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.util.*

class Cell : MultiItemEntity {

    var text: String? = null
    var type: Int = 0

    override fun getItemType(): Int {
        return type
    }

    companion object {

        val MULTI_CELL = 0
        val MULTI_TOP = 1
        val MULTI_LEFT = 2

        val SPAN_COUNT = 31
        val CELL_COUNT = 3000

        fun init(): List<Cell> {
            val list = ArrayList<Cell>()

            val c = SPAN_COUNT - 1
            val size = CELL_COUNT + c + 1 + CELL_COUNT / c + if (CELL_COUNT % c > 0) 1 else 0
            var index = 0
            for (i in 0 until size) {
                val cell = Cell()
                if (i == 0) {
                    cell.text = "\\"
                } else if (i < SPAN_COUNT) {
                    cell.text = "col " + (i - 1)
                    cell.type = MULTI_TOP
                } else if (i % SPAN_COUNT == 0) {
                    cell.text = "row"
                    cell.type = MULTI_LEFT
                } else {
                    cell.text = "~ " + index++
                    cell.type = MULTI_CELL
                }
                list.add(cell)
            }
            return list
        }
    }
}
