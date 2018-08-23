package com.y.mvp.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.y.R;

public class AppToolbar extends Toolbar {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private Context mContext;

    public AppToolbar(Context context) {
        this(context,null);
    }

    public AppToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public AppToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mIvBack = findViewById(R.id.iv_toolbar_icon);
        mTvTitle = findViewById(R.id.tv_toolbar_title);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof Activity){
                    ((Activity)mContext).finish();
                }
            }
        });
    }

    public TextView getTitleView() {
         return mTvTitle;
    }

    public ImageView getIconView() {
        return mIvBack;
    }

}
