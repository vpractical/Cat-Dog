package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.y.R;
import com.y.mvp.activity.presenter.VideoContract;
import com.y.mvp.activity.presenter.VideoPresenter;
import com.y.mvp.activity.video.YMediaController;
import com.y.mvp.base.BaseActivity;
import com.y.util.AppUtil;
import com.y.util.ScreenUtil;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.VideoView;

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, VideoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.vv_video)
    VideoView vv;
    @BindView(R.id.container_vv)
    FrameLayout containerVV;
    private YMediaController mc;

    private int fitHeight;//videoView的高度

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
        mToolBar.getTitleView().setText("美拍");
//        String path = "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8";
        String path = AppUtil.context().getExternalCacheDir().getAbsolutePath() + "/vv.flv";

        mc = new YMediaController(mActivity,vv);
        mc.show(15000);
        mc.setMediaPlayer(vv);
        mc.setPadding(0,0,0,0);

        vv.setMediaController(mc);
//        vv.setVideoPath(path);
        vv.setVideoURI(Uri.parse(path));
        vv.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//        vv.setVideoLayout(VideoView.VIDEO_LAYOUT_ORIGIN, 0);
        vv.requestFocus();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
                int vw = mp.getVideoWidth();
                int vh = mp.getVideoHeight();

                fitHeight = (int) (1f * vh / vw * ScreenUtil.getScreenWidth());
//                setFitScreen();
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

    private void setFitScreen(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,fitHeight);
        containerVV.setLayoutParams(layoutParams);
        CenterLayout.LayoutParams layoutParams1 = new CenterLayout.LayoutParams(
                CenterLayout.LayoutParams.MATCH_PARENT,
                CenterLayout.LayoutParams.MATCH_PARENT,
                0,0);
        vv.setLayoutParams(layoutParams1);
    }

    private void setFullScreen() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        containerVV.setLayoutParams(layoutParams);
        CenterLayout.LayoutParams layoutParams1 = new CenterLayout.LayoutParams(
                CenterLayout.LayoutParams.MATCH_PARENT,
                CenterLayout.LayoutParams.MATCH_PARENT,
                0,0);
        vv.setLayoutParams(layoutParams1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv.stopPlayback();
    }
}
