package com.y.app;

import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.y.config.GlideConfig;
import com.y.config.UmConfig;
import com.y.imageloader.ImageLoader;
import com.y.mvp.app.MvpApplication;
import com.y.util.AppUtil;
import com.y.util.DiskCache;

public class CatDogApplication extends MvpApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        AppUtil.init(this);
        DiskCache.init(this);
        MultiDex.install(this);
        UmConfig.init(this);
        ImageLoader.getInstance().strategy(new GlideConfig()).configure();
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
