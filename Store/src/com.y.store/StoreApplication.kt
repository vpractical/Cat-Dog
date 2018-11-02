package com.y.store

import android.app.Application
import com.y.route.Router
import com.y.util.AppUtil

class StoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtil.init(this)
        Router.init(this)
    }

}