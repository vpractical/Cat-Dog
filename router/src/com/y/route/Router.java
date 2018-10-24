package com.y.route;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }

    private Router() {
    }

    private Map<String, Pair<String, String>> routes = new HashMap<>();
    private Intent intent;
    private String path;
    private Context context;

    public void put(String path, Class<?> clz) {
        Log.e("-------------" + path,clz.getName());
        Pair<String, String> pair = new Pair<>(clz.getPackage().getName(),clz.getName());
        routes.put(path, pair);
    }

    public Pair<String,String> get(String path) {
        return routes.get(path);
    }

    public Router with(Context context) {
        this.context = context.getApplicationContext();
        return this;
    }

    public Router target(String path) {
        this.path = path;
        intent = new Intent();
        if (routes.containsKey(path)) {
            Pair<String,String> pair = routes.get(path);
            intent.setClassName(pair.first,pair.second);
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
     * getClass().getDeclaredFields()
     * field.getDeclaredAnnotations()
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static void init(Application application) {
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activity : packageInfo.activities) {
                if (!activity.name.startsWith("com.y.")) continue;
                Class<?> clz = Class.forName(activity.name);
                if (!clz.isAnnotationPresent(Route.class)) continue;
                Route route = clz.getDeclaredAnnotation(Route.class);
                Router.getInstance().put(route.path(), clz);
//                for (Annotation a:annotations) {
//                    if(a instanceof Route){
//                        Router.getInstance().put(((Route) a).path(),clz);
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
