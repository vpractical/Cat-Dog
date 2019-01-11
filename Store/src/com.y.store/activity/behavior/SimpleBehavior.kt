package com.y.store.activity.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * 参考：https://www.jianshu.com/p/b987fad8fcb4
 */
class SimpleBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {


    private var lastY = 0f

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {

        val height = child.measuredHeight
        var dy = dependency.y - lastY
        dy *= 0.6f
        if (dy < 0) {
            if (child.y - dy < 0) {
                child.offsetTopAndBottom(-dy.toInt())
            } else if (child.y - dy > 0 && child.y < 0) {
                child.offsetTopAndBottom(-child.y.toInt())
            }
        } else {
            if (child.y - dy > -height) {
                child.offsetTopAndBottom(-dy.toInt())
            } else if (child.y - dy < -height && child.y > -height) {
                child.offsetTopAndBottom((-child.y - height).toInt())
            }
        }
        lastY = dependency.y
        return super.onDependentViewChanged(parent, child, dependency)
    }

}