package com.y.mvp.activity.video;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.y.R;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class YMediaController extends MediaController {
    private GestureDetector mGestureDetector;
    private VideoView videoView;

    //videoview 用于对视频进行控制的等，activity为了退出
    public YMediaController(Context context, VideoView videoView) {
        super(context);
        this.videoView = videoView;
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
        //加入布局文件,MediaController布局名称
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_mediacontroller,this);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当收拾结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        //双击暂停或开始
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }
    }
    //隐藏/显示
    private void toggleMediaControlsVisiblity(){
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }
    //播放与暂停
    private void playOrPause(){
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
    }

}
