package com.y.store.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.y.store.R
import com.y.store.adapter.PictureAdapter
import com.y.store.base.BaseActivity
import kotlinx.android.synthetic.main.store_activity_behavior_test.*

open class BehaviorTestActivity:BaseActivity() {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, BehaviorTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var mData = ArrayList<String>()
    private lateinit var mAdapter: PictureAdapter

    override fun init() {
        initData()
        mAdapter = PictureAdapter(mActivity,R.layout.store_item_picture,mData)
        storeBehaviorRv.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false)
        storeBehaviorRv.adapter = mAdapter


    }

    override fun layout(): Int {
        return R.layout.store_activity_behavior_test
    }


    private fun initData(){
        mData.clear()
        for (i in 1..20){
            mData.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2377598664,3646734033&fm=27&gp=0.jpg")
        }
    }
}