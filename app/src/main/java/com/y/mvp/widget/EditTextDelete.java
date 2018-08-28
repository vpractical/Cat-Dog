package com.y.mvp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.y.R;
import com.y.util.ScreenUtil;

/**
 * 自定义EditText控件
 */
public class EditTextDelete extends AppCompatEditText {
    private Drawable imgDisable;
    private Context mContext;
    private int padding;
    private final static int SPACE = 5;
    private Drawable left, top, right, bottom;

    public EditTextDelete(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextDelete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        Drawable[] drawable = getCompoundDrawables();
        left = drawable[0];
        top = drawable[1];
        right = drawable[2];
        bottom = drawable[3];
        imgDisable = mContext.getResources().getDrawable(R.drawable.delete_gray);
        padding = ScreenUtil.dp2px(SPACE);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    // 设置删除图片
    private void setDrawable() {
        if (length() >= 1) {
            setCompoundDrawablesWithIntrinsicBounds(left, top, imgDisable, bottom);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }

        setCompoundDrawablePadding(padding);//设置图片与内容间隔
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getX();
            int eventY = (int) event.getY();
            Rect rect = new Rect();
            getLocalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY)) setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}

