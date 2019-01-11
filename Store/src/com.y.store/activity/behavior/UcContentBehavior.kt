//package com.y.store.activity.behavior
//
//import android.content.Context
//import android.support.design.widget.CoordinatorLayout
//import android.util.AttributeSet
//import android.view.View
//import com.y.store.R
//import com.y.store.activity.behavior.orinal.HeaderScrollingViewBehavior
//
//class UcContentBehavior(context: Context, attrs: AttributeSet) : HeaderScrollingViewBehavior(context, attrs) {
//    private var mixDimen: Float = context.resources.getDimension(R.dimen.uc_title_height) + context.resources.getDimension(R.dimen.uc_tab_height)
//
//    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        return isDependsOn(dependency)
//    }
//
//    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        var transY = dependency.translationY
//        if (transY < -mixDimen) {
//            transY = -mixDimen
//        }
//        child.y = transY
//        return true
//    }
//
//    override fun findFirstDependency(views: MutableList<View>): View? {
//        for (view in views) {
//            if(isDependsOn(view)) return view
//        }
//        return null
//    }
//
//    /**
//     * 是否是依赖的view
//     */
//    private fun isDependsOn(view: View?): Boolean {
//        return view?.id == R.id.storeBehaviorHeader
//    }
//
//}