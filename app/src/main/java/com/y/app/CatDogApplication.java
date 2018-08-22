package com.y.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.y.mvp.app.MvpApplication;
import com.y.util.AppUtil;
import com.y.util.DiskCache;

public class CatDogApplication extends MvpApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        AppUtil.init(this);
        DiskCache.init(this);

    }
}
