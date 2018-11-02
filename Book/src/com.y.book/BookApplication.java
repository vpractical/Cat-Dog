package com.y.book;

import android.app.Application;

import com.y.route.Router;
import com.y.util.AppUtil;
import com.y.util.L;

public class BookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("hhhhhhhhhhhhhhhhhhhhhhhhhhhhh0---");
        AppUtil.init(this);
        Router.init(this);
    }
}
