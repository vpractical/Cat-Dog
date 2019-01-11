package com.y.store.activity.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class RecyclerViewBehavior(context: Context?, attrs: AttributeSet?): CoordinatorLayout.Behavior<RecyclerView>(context,attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {

        var dis = (dependency.translationY + dependency.height)

        if(dis < 0) {
            dis = 0f
        }

        child.translationY = dis

        return true
    }


}