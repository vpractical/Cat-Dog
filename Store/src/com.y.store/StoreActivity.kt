package com.y.store

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.y.router_annotations.Route

@Route(path = "store/main")
class StoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store_activity_main)

        initListener()
    }

    private fun initListener(){

    }
}
