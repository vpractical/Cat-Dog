package com.y.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.y.store.R
import com.y.store.activity.StoreActivity
import com.y.store.adapter.PictureAdapter
import com.y.store.base.BaseFragment
import com.y.util.L
import com.y.util.T
import kotlinx.android.synthetic.main.store_fragment_picture.*

class PictureFragment:BaseFragment() {

    companion object {
        fun newInstance(index:Int): BaseFragment{
            val fragment = PictureFragment()
            val bundle = Bundle()
            bundle.putInt("index",index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mData = ArrayList<String>()
    private lateinit var mAdapter:PictureAdapter
    private var index = -1

    override fun layout(): Int {
        return R.layout.store_fragment_picture
    }

    override fun init() {
        index = arguments!!.getInt("index")
        initData()
        mAdapter = PictureAdapter(mActivity,R.layout.store_item_picture,mData)
        rvStorePicture.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false)
        rvStorePicture.adapter = mAdapter

        initListener()
    }

    private fun initData(){
        mData.clear()
        for (i in 1..20){
            mData.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2377598664,3646734033&fm=27&gp=0.jpg")
        }
    }

    private fun initListener() {
        mAdapter.setOnItemClickListener{
            pos ->
            T.show("pos = $pos")
            L.e("pos = $pos")
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
//        L.e("setUserVisibleHint$index",isVisibleToUser.toString())
    }

    /**
     * 扩展函数
     */
    private fun StoreActivity.createNew(i: Int){
//        L.e("-------new$i")
        this.ddd()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (mActivity as StoreActivity).createNew(index)
    }
}
