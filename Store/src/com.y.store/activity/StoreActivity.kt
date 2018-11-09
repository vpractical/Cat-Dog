package com.y.store

import com.y.router_annotations.Route
import com.y.store.base.BaseActivity

@Route(path = "store/main")
class StoreActivity : BaseActivity() {

    override fun layout(): Int {
        return R.layout.store_activity_main;
    }

    override fun init() {

        initListener();
    }

    private fun initListener(){

    }
}
