package com.y.mvp.activity.video;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.VideoView;

public class YVideoView extends VideoView {
    public YVideoView(Context context) {
        super(context);
    }

    public YVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setVideoLayout(int layout, float aspectRatio) {
//        ViewGroup.LayoutParams lp = getLayoutParams();
//        ViewGroup parent = (ViewGroup) getParent();
//        lp.width = width;
//        lp.height = height;
//        L.e("VIDEO=====",lp.width+"============"+ lp.height);
//        setLayoutParams(lp);
//        getHolder().setFixedSize(width,  height);
    }
}
