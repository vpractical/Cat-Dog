package com.y.mvp.activity.video;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.y.R;
import com.y.util.L;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class YMediaController extends MediaController {
    private static final int HIDEFRAM = 0;//控制提示窗口的显示

    private GestureDetector mGestureDetector;
    private ImageView img_back;//返回按钮
    private VideoView videoView;
    private Activity activity;
    private View mVolumeBrightnessLayout;//提示窗口
    private ImageView mOperationBg;//提示图片
    private TextView mOperationTv;//提示文字
    private AudioManager mAudioManager;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;


    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case HIDEFRAM://隐藏提示窗口
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    mOperationTv.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private ImageView mIvScale;


    public YMediaController(Activity activity,View container,VideoView videoView){
        super(activity,true,container);
        this.videoView = videoView;
        this.activity = activity;
        mGestureDetector = new GestureDetector(activity, new ControllerGestureListener());
    }

    @Override
    protected View makeControllerView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_mediacontroller,this);
        //获取控件
        img_back =  v.findViewById(getResources().getIdentifier("mediacontroller_top_back", "id", getContext().getPackageName()));
        //缩放控件
        mIvScale =  v.findViewById(getResources().getIdentifier("mediacontroller_scale", "id", getContext().getPackageName()));

        //声音控制
        mVolumeBrightnessLayout =  v.findViewById(R.id.operation_volume_brightness);
        mOperationBg =  v.findViewById(R.id.operation_bg);
        mOperationTv =  v.findViewById(R.id.operation_tv);
        mOperationTv.setVisibility(View.GONE);
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //注册事件监听
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    activity.finish();
                }
            }
        });
        mIvScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    switch (activity.getResources().getConfiguration().orientation) {
                        case Configuration.ORIENTATION_LANDSCAPE://横屏
                            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case Configuration.ORIENTATION_PORTRAIT://竖屏
                            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }

                }
            }
        });
        return v;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        // 隐藏
        myHandler.removeMessages(HIDEFRAM);
        myHandler.sendEmptyMessageDelayed(HIDEFRAM, 1);
    }

    private class ControllerGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            L.e("onSingleTapUp");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            L.e("onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        /**
         * 因为使用的是自定义的mediaController 当显示后，mediaController会铺满屏幕，
         * 所以VideoView的点击事件会被拦截，所以重写控制器的手势事件，
         * 将全部的操作全部写在控制器中，
         * 因为点击事件被控制器拦截，无法传递到下层的VideoView，
         * 所以 原来的单机隐藏会失效，作为代替，
         * 在手势监听中onSingleTapConfirmed（）添加自定义的隐藏/显示，
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当手势结束，并且是单击结束时，控制器隐藏/显示
            L.e("onSingleTapConfirmed");
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            L.e("onDown");
            return true;
        }

        //滑动事件监听
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            int x = (int) e2.getRawX();
            Display disp = activity.getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();
            if (mOldX > windowWidth * 3.0 / 4.0) {// 右边滑动 屏幕 3/4
                onVolumeSlide((mOldY - y) / windowHeight);
            } else if (mOldX < windowWidth * 1.0 / 4.0) {// 左边滑动 屏幕 1/4
                onBrightnessSlide((mOldY - y) / windowHeight);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            L.e("onDoubleTap");
            playOrPause();
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        if (index >= 10) {
            mOperationBg.setImageResource(R.drawable.volmn_100);
        } else if (index >= 5 && index < 10) {
            mOperationBg.setImageResource(R.drawable.volmn_60);
        } else if (index > 0 && index < 5) {
            mOperationBg.setImageResource(R.drawable.volmn_30);
        } else {
            mOperationBg.setImageResource(R.drawable.volmn_no);
        }
        //DecimalFormat    df   = new DecimalFormat("######weather0.00");
        mOperationTv.setText((int) (((double) index / mMaxVolume) * 100) + "%");
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);

        }


        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);

        mOperationTv.setText((int) (lpa.screenBrightness * 100) + "%");
        if (lpa.screenBrightness * 100 >= 90) {
            mOperationBg.setImageResource(R.drawable.light_100);
        } else if (lpa.screenBrightness * 100 >= 80 && lpa.screenBrightness * 100 < 90) {
            mOperationBg.setImageResource(R.drawable.light_90);
        } else if (lpa.screenBrightness * 100 >= 70 && lpa.screenBrightness * 100 < 80) {
            mOperationBg.setImageResource(R.drawable.light_80);
        } else if (lpa.screenBrightness * 100 >= 60 && lpa.screenBrightness * 100 < 70) {
            mOperationBg.setImageResource(R.drawable.light_70);
        } else if (lpa.screenBrightness * 100 >= 50 && lpa.screenBrightness * 100 < 60) {
            mOperationBg.setImageResource(R.drawable.light_60);
        } else if (lpa.screenBrightness * 100 >= 40 && lpa.screenBrightness * 100 < 50) {
            mOperationBg.setImageResource(R.drawable.light_50);
        } else if (lpa.screenBrightness * 100 >= 30 && lpa.screenBrightness * 100 < 40) {
            mOperationBg.setImageResource(R.drawable.light_40);
        } else if (lpa.screenBrightness * 100 >= 20 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_30);
        } else if (lpa.screenBrightness * 100 >= 10 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_20);
        }

    }


    /**
     * 设置视频文件名
     *
     * @param name
     */
    public void setVideoName(String name) {
        super.setFileName(name);
    }

    /**
     * 隐藏或显示
     */
    private void toggleMediaControlsVisiblity() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
    }
}
