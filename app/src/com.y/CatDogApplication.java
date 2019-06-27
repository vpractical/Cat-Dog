package com.y;

import android.content.res.Configuration;
import android.os.Debug;
import android.support.multidex.MultiDex;

import com.y.config.GlideConfig;
import com.y.config.UmConfig;
import com.y.imageloader.ImageLoader;
import com.y.mvp.app.MvpApplication;
import com.y.route.Router;
import com.y.util.AppUtil;
import com.y.util.DiskCache;

public class CatDogApplication extends MvpApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        //分析方法耗时
        //启动优化等等
        Debug.startMethodTracing(getExternalCacheDir() + "/trace3.trace");
        AppUtil.init(this);
        DiskCache.init(this);
        MultiDex.install(this);
//        UmConfig.init(this);
        ImageLoader.getInstance().strategy(new GlideConfig()).configure();
        Router.init(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UmConfig.init(CatDogApplication.this);
            }
        }).start();
        Debug.stopMethodTracing();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
