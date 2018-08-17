package com.y.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtil {

    private static Context applicationContext;


    public static void init(Application application){
        applicationContext = application;
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




}
