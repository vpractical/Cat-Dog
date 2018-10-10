package com.y.mvp.activity.video;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.activity.presenter.VideoContract;
import com.y.mvp.activity.presenter.VideoPresenter;
import com.y.mvp.base.BaseActivity;
import com.y.util.AppUtil;
import com.y.util.T;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

    public static void start(Context context,String url,String desc) {
        Intent intent = new Intent();
        intent.setClass(context, VideoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("url",url);
        intent.putExtra("desc",desc);
        context.startActivity(intent);
    }

    @BindView(R.id.vv_video)
    VideoView vv;
    @BindView(R.id.container_vv)
    FrameLayout containerVV;
    @BindView(R.id.tv_video_detail_desc)
    TextView tvDesc;
    private YMediaController mc;
    private String url,desc;

    @Override
    protected int getLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");
        tvDesc.setText(desc);
        if(!Vitamio.isInitialized(AppUtil.context())){
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    T.show("Vitamio未安装");
                    mActivity.finish();
                }
            },2000);
            return;
        }

        mc = new YMediaController(mActivity,containerVV,vv);
        mc.show(7000);
        mc.setMediaPlayer(vv);
//        mc.setPadding(weather0,weather0,weather0,weather0);

        vv.setMediaController(mc);
        vv.setVideoPath(url);
        mc.setVideoName(desc);
        vv.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        vv.setBufferSize(10240);
        vv.requestFocus();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
//                vv.setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, weather0);
            }
        });
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        vv.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });
        vv.start();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setFullScreen();
//        } else {
//            setFitScreen();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv.stopPlayback();
    }
}
