package com.y.mvp.form;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ParentLinearLayout extends LinearLayout {
    public ParentLinearLayout(Context context) {
        super(context);
    }

    public ParentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
