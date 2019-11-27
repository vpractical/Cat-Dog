package com.y.route;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.y.router_annotations.Route;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由映射保存，跳转处理
 */
public class Router {
    private static final Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }

    private Router() {
    }

    private Map<String, Class> routes = new HashMap<>();
    private Intent intent;
    private String path;
    private Context context;

    private void put(String path, Class clz) {
        routes.put(path, clz);
    }

    public Class get(String path) {
        return routes.get(path);
    }

    public Router with(Context context) {
        this.context = context;
        return this;
    }

    public Router target(String path) {
        this.path = path;
        intent = new Intent();
        if (routes.containsKey(path)) {
            intent.setClass(context,routes.get(path));
        } else {
            Log.e("Router", "Router has no path for key " + path);
        }
        return this;
    }

    public void start() {
        if(routes.get(path) != null){
            context.startActivity(intent);
        }
    }

    public Router param(String key, String str) {
        intent.putExtra(key, str);
        return this;
    }

    public Router param(String key, int i) {
        intent.putExtra(key, i);
        return this;
    }

    public Router param(String key, boolean b) {
        intent.putExtra(key, b);
        return this;
    }

    public Router param(String key, Bundle bundle) {
        intent.putExtra(key, bundle);
        return this;
    }

    /**
     * 寻找带有Route注解的activity
     */
    public static void init(Application application) {
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activity : packageInfo.activities) {
            if (!activity.name.startsWith("com.y.")) continue;
            Class<?> clz = Class.forName(activity.name);
            Annotation[] annotations = clz.getDeclaredAnnotations();
            for (Annotation a:annotations) {
                if(a instanceof Route){
                    Router.getInstance().put(((Route) a).path(),clz);
                }
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
