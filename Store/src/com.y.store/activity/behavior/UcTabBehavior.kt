package com.y.store.activity.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.y.store.R

class UcTabBehavior (context: Context, attrs: AttributeSet): CoordinatorLayout.Behavior<LinearLayout>(context,attrs) {

    private val tabHeight = context.resources.getDimension(R.dimen.uc_tab_height)
    private val titleHeight = context.resources.getDimension(R.dimen.uc_title_height)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        return isDependsOn(dependency)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {

        val 比例 = (dependency.measuredHeight - titleHeight) / (dependency.measuredHeight - titleHeight - tabHeight)

        var transY = dependency.translationY * 比例

        if(transY < dependency.measuredHeight - titleHeight){
            transY = dependency.measuredHeight - titleHeight;
        }

        child.translationY = transY
        return true
    }



    /**
     * 是否是依赖的view
     */
    private fun isDependsOn(view: View?):Boolean{
        return view?.id == R.id.storeBehaviorHeader
    }

}