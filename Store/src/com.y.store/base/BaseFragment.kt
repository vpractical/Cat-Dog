package com.y.store.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.y.permissionlib.PermissionCat
import com.y.util.L

abstract class BaseFragment : Fragment() {

    protected lateinit var mActivity: Activity
    private var unBinder: Unbinder? = null
    private var rootView: View? = null
    private var initialized = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        L.e("创建了Fragment ：" + javaClass.simpleName)
        mActivity = activity as Activity
        initialized = rootView != null
        if (!initialized) {
            rootView = inflater.inflate(layout(), container, false)
            unBinder = ButterKnife.bind(this, rootView!!)
        }

        var vg = rootView!!.parent
        if (vg != null) {
            (vg as ViewGroup).removeAllViews()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!initialized){
            init()
        }
    }

    abstract fun layout(): Int
    abstract fun init()

    override fun onDestroy() {
        super.onDestroy()
        L.e("销毁了Fragment ：" + javaClass.simpleName)
        if (unBinder != null) {
            unBinder!!.unbind()
            unBinder = null
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionCat.onRequestPermissionsResult(this, permissions, grantResults)
    }
}