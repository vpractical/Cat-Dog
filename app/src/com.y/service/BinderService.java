package com.y.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.y.util.AppUtil;
import com.y.util.L;

public class BinderService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.e("BinderService", AppUtil.processName());
        return START_STICKY;
    }

    public int add2(int a,int b){
        return a + b;
    }

    private Binder binder = new Stub();
    public class Stub extends Binder{
        public BinderService getService(){
            return BinderService.this;
        }
    }

}
