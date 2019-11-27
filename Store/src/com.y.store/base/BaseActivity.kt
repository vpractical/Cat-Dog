package com.y.store.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import butterknife.ButterKnife
import com.y.permissionlib.PermissionCat
import com.y.util.KeyBoardUtil

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mActivity:BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        setContentView(layout())
        ButterKnife.bind(this)
        init()
    }

    abstract fun layout(): Int
    abstract fun init()

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action == MotionEvent.ACTION_DOWN){
            val v: View? = currentFocus
            if (v == null && KeyBoardUtil.isShouldHideKeyboard(v, ev)) {
                KeyBoardUtil.hideSoftInput(v,mActivity)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionCat.onRequestPermissionsResult(this,permissions,grantResults)

    }

}