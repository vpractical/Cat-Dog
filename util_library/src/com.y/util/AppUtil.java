package com.y.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.Stack;

public class AppUtil {
    private static final String TAG = "AppUtil";

    private static Application applicationContext;
    /**
     * 前台activity的数量，
     */
    private static int ISAPPRUNBACKGROUND;
    /**
     * 栈中的activity
     */
    private static Stack<Activity> aliveActivities = new Stack<>();



    public static void init(Application application){
        applicationContext = application;
        registerCycle();
    }

    public static Context context(){
        return applicationContext;
    }


    /**
     * 获取包名
     * @return
     */
    public static String packageName(){
        return applicationContext.getPackageName();
    }


    /**
     * 获取VersionName(版本名称)
     * @return
     * 失败时返回""
     */
    public static String versionName(){
        PackageManager packageManager = applicationContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取VersionCode(版本号)
     * @return
     * 失败时返回-1
     */
    public static int versionCode(){
        PackageManager packageManager = applicationContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }



    public static void put(Activity activity){
        aliveActivities.add(activity);
    }

    public static void remove(Activity activity){
        if(aliveActivities.contains(activity)){
            aliveActivities.remove(activity);
        }
    }

    public static Activity current(){
        if(!aliveActivities.isEmpty()){
            return aliveActivities.lastElement();
        }
        return null;
    }

    public static boolean isTop(Activity activity){
        return !aliveActivities.isEmpty() && aliveActivities.lastElement() == activity;
    }

    public static void start(Class<? extends Activity> activity){
        applicationContext.startActivity(new Intent(applicationContext,activity));
    }

    public static void finish(Activity activity){
        activity.finish();
    }

    public static void finishAll(){
        for (int i = 0; i < aliveActivities.size(); i++) {
            if(aliveActivities.get(i) != null){
                aliveActivities.get(i).finish();
            }
        }
        aliveActivities.clear();
    }

    /**
     * 查看app是否在前台
     */
    public static boolean isRunBackground(){
        return ISAPPRUNBACKGROUND == 0;
    }

    private static void registerCycle(){
        applicationContext.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                L.e(TAG,"创建了：" + activity.getClass().getSimpleName());
                put(activity);
//                new ScreenAdaptation(activity, 1080).register();
            }

            @Override
            public void onActivityStarted(Activity activity) {
                ISAPPRUNBACKGROUND++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                ISAPPRUNBACKGROUND--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                L.e(TAG,"销毁了：" + activity.getClass().getSimpleName());
                remove(activity);
            }
        });
    }
}
