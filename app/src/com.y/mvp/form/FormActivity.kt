package com.y.mvp.form

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.y.R
import com.y.adapter.FormAdapter
import com.y.bean.Cell
import com.y.util.L
import com.y.util.ScreenUtil
import com.y.util.T

class FormActivity : AppCompatActivity() {

    private var mActivity: Activity? = null
    internal var rvForm: RecyclerView
    private val mData = Cell.init()
    private var mAdapter: FormAdapter? = null
    private var translateX: Float = 0.toFloat()
    private var translateMax: Float = 0.toFloat()
    private var mGestureDetector: GestureDetector? = null

    /**
     * fling时，移动距离计算
     */
    private var decrease = 0f
    private var v: Float = 0.toFloat()
    private val flyTask = Runnable {
        val dis = v * 16 / 1000
        move(-dis)
        fling(v)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        mActivity = this
        init()
        initListener()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector!!.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    private fun init() {
        val glManager = GridLayoutManager(mActivity, Cell.SPAN_COUNT, RecyclerView.VERTICAL, false)
        rvForm = findViewById(R.id.rv_form)
        val lp = rvForm.layoutParams
        val itemW = 200
        lp.width = itemW * Cell.SPAN_COUNT
        translateMax = (itemW * Cell.SPAN_COUNT - ScreenUtil.getScreenWidth()).toFloat()
        rvForm.layoutParams = lp
        rvForm.layoutManager = glManager
        mAdapter = FormAdapter(mData)
        rvForm.adapter = mAdapter
    }

    private fun initListener() {

        mAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            T.show(mData[position].text)
            L.e(mData[position].text)
        }

        mGestureDetector = GestureDetector(mActivity, object : GestureDetector.SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent): Boolean {
                return super.onDown(e)
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                move(distanceX)
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                decrease = velocityX / 100
                fling(velocityX)
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
    }

    private fun fling(vx: Float) {
        rvForm.removeCallbacks(flyTask)
        if (vx > 0 && vx < decrease || vx < 0 && vx > decrease) {
            return
        }
        decrease *= 1.05f
        v = vx - decrease
        rvForm.postDelayed(flyTask, 16)
    }

    private fun move(x: Float) {
        translateX -= x
        if (translateX > 0) {
            translateX = 0f
        } else if (translateX < -translateMax) {
            translateX = -translateMax
        }
        rvForm.translationX = translateX
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, FormActivity::class.java)
            context.startActivity(intent)
        }
    }
}
