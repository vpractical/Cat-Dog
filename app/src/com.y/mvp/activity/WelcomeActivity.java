package com.y.mvp.activity;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.y.R;
import com.y.bean.Login;
import com.y.config.Key;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.login.LoginActivity;
import com.y.mvp.view.CustomVideoView;
import com.y.permissionlib.PermissionCallback;
import com.y.permissionlib.PermissionCat;
import com.y.util.SPUtil;

import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private static final String PERM_INTERNET = Manifest.permission.INTERNET;
    private static final String PERM_STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERM_STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERM_CAMERA = Manifest.permission.CAMERA;

    private CustomVideoView vvGuide;
    private TextView tvDesc;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Runnable countdownRunnable;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {
        String[] perms = {PERM_INTERNET, PERM_STORAGE_READ, PERM_STORAGE_WRITE,PERM_CAMERA};
        if (PermissionCat.has(this, perms)) {
            getWindow().setBackgroundDrawable(null);
            tvDesc = findViewById(R.id.tv_main_desc);

            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvDesc.setVisibility(View.GONE);
                }
            }, 2200);

            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initVideo();
                }
            }, 2000);

            final Login login = (Login) SPUtil.getSingleObject(Key.LOGIN_KEY, Login.class);
            mainHandler.postDelayed(countdownRunnable = new Runnable() {
                @Override
                public void run() {
                    if (login != null && login.validTime > System.currentTimeMillis()) {
                        MainActivity.start(mActivity);
                    } else {
                        LoginActivity.start(mActivity);
                    }
                    finish();
                }
            }, 10000);
        } else {
            PermissionCat.request("应用必须权限", this, new PermissionCallback() {
                @Override
                public void onGranted(String[] strings, List<String> list) {
                    if (strings.length == list.size()) {
                        init();
                    }
                }

                @Override
                public void onDenied(String[] strings, List<String> list) {
                    finish();
                }
            }, perms);
        }
    }

    private void initVideo() {
        vvGuide = findViewById(R.id.vv_main_guide);
        vvGuide.setVisibility(View.VISIBLE);
        String path = "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1543129654546.mp4";
        vvGuide.setVideoPath(path);
        vvGuide.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vvGuide.start();
            }
        });
        vvGuide.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(vvGuide != null){
            vvGuide.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(vvGuide != null){
            vvGuide.stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacks(countdownRunnable);
    }
}
