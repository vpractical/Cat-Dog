package com.y.store.adapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.y.store.base.BaseActivity

class MainVpAdapter: FragmentPagerAdapter {
    private var mActivity: Activity
    private var list: ArrayList<Fragment>

    constructor(activity: BaseActivity,list: ArrayList<Fragment>): super(activity.supportFragmentManager){
        this.mActivity = activity
        this.list = list
    }

    override fun getCount(): Int {
        return list.size
    }


    override fun getItem(p0: Int): Fragment {
        return list[p0]
    }

}