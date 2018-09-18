package com.y.mvp.form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.y.R;
import com.y.adapter.FormAdapter;
import com.y.bean.Cell;
import com.y.util.L;
import com.y.util.ScreenUtil;
import com.y.util.T;

import java.util.List;

public class FormActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, FormActivity.class);
        context.startActivity(intent);
    }

    private Activity mActivity;
    RecyclerView rvForm;
    private List<Cell> mData = Cell.init();
    private FormAdapter mAdapter;
    private float translateX, translateMax;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mActivity = this;
        init();
        initListener();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private void init() {
        GridLayoutManager glManager = new GridLayoutManager(mActivity, Cell.SPAN_COUNT, RecyclerView.VERTICAL, false);
        rvForm = findViewById(R.id.rv_form);
        ViewGroup.LayoutParams lp = rvForm.getLayoutParams();
        int itemW = 200;
        lp.width = itemW * Cell.SPAN_COUNT;
        translateMax = itemW * Cell.SPAN_COUNT - ScreenUtil.getScreenWidth();
        rvForm.setLayoutParams(lp);
        rvForm.setLayoutManager(glManager);
        mAdapter = new FormAdapter(mData);
        rvForm.setAdapter(mAdapter);
    }

    private void initListener() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T.show(mData.get(position).text);
                L.e(mData.get(position).text);
            }
        });

        mGestureDetector = new GestureDetector(mActivity, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return super.onDown(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                move(distanceX);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                decrease = velocityX / 100;
                fling(velocityX);
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * fling时，移动距离计算
     */
    private float decrease = 0;
    private float v;
    private Runnable flyTask = new Runnable() {
        @Override
        public void run() {
            float dis = v * 16 / 1000;
            move(-dis);
            fling(v);
        }
    };

    private void fling(float vx) {
        rvForm.removeCallbacks(flyTask);
        if (vx > 0 && vx < decrease || (vx < 0 && vx > decrease)) {
            return;
        }
        decrease *= 1.05;
        v = vx - decrease;
        rvForm.postDelayed(flyTask, 16);
    }

    private void move(float x) {
        translateX -= x;
        if (translateX > 0) {
            translateX = 0;
        } else if (translateX < -translateMax) {
            translateX = -translateMax;
        }
        rvForm.setTranslationX(translateX);
    }
}
