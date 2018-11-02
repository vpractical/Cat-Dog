package com.y.mvp.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 例如侧滑删除到最后一个时会报数组下标越界，捕获处理
 */
public class CatLinearlayoutManager extends LinearLayoutManager {
    public CatLinearlayoutManager(Context context) {
        super(context);
    }

    public CatLinearlayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CatLinearlayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try{
            super.onLayoutChildren(recycler, state);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
