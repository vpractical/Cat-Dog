package com.y.store.activity

import com.y.router_annotations.Route
import com.y.store.R

@Route(path = "store/behavior2")
class BehaviorTestActivity2: BehaviorTestActivity() {

    override fun layout(): Int {
        return R.layout.store_activity_behavior_test2
    }

}