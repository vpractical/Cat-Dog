package com.y.mvp.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.y.mvp.R;

public class LoadingDialog extends Dialog {

    protected Context mContext;
    protected WindowManager.LayoutParams mLayoutParams;
    private Animation mLoadAnimation;
    private ImageView mIvProgress;

    public LoadingDialog(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mIvProgress = new ImageView(context);
        mIvProgress.setBackgroundResource(R.drawable.loading);
        ViewGroup.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = 60;
        lp.height = 60;
        mIvProgress.setLayoutParams(lp);
        mLoadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_loading);
        LinearLayout rootView = new LinearLayout(context);
        LinearLayout.LayoutParams rootLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rootLp.gravity = Gravity.CENTER;
        rootView.setLayoutParams(rootLp);
        rootView.addView(mIvProgress);
        setContentView(rootView);

        setOnWhole();
    }

    /**
     * 隐藏头部导航栏状态栏
     */
    public void skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置全屏显示
     */
    public void setFullScreen() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 设置高度为match_parent
     */
    public void setFullScreenHeight() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    public void setOnWhole() {
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }


    //多次请求弹框问题
    private int showCount = 0;

    @Override
    public synchronized void show() {
        if (!isShowing()) {
            super.show();
            if (mIvProgress != null) {
                mIvProgress.startAnimation(mLoadAnimation);
            }
        }
        showCount = showCount + 1;
    }

    @Override
    public synchronized void dismiss() {
        showCount = showCount - 1;
        if (showCount <= 0) {
            showCount = 0;
            super.dismiss();
            if (mIvProgress != null) {
                mIvProgress.clearAnimation();
            }
        }
    }
}
