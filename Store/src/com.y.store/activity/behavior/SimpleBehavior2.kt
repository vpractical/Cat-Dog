package com.y.store.activity.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SimpleBehavior2(context: Context?, attrs: AttributeSet?): CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {


    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if(target !is RecyclerView){
            return
        }
        val rv = target

        var finalY = child.translationY - dy
        consumed[1] = dy
        if(finalY < -child.height){
            finalY = -child.height.toFloat()
            consumed[1] = -child.height - child.translationY.toInt()
        }else if(finalY > 0){
            finalY = 0.toFloat()
            consumed[1] = 0 - child.translationY.toInt()
        }
        child.translationY = finalY
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

}