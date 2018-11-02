package com.y.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.y.R;

public class AppToolbar extends Toolbar {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private Context mContext;
    private ViewGroup mContainer;

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
        mContainer = findViewById(R.id.container_toolbar);
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

    public TextView getRightText(){
        TextView tv = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,0,10,0);
        tv.setLayoutParams(lp);
        tv.setTextColor(Color.WHITE);
        mContainer.addView(tv);
        return tv;
    }

    /**
     * 待调试
     */
    public ImageButton getRightIcon(){
        ImageButton ib = new ImageButton(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,0,10,0);
        ib.setLayoutParams(lp);
        ib.setImageResource(R.drawable.more_dark);
        ib.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mContainer.addView(ib);
        return ib;
    }
}
