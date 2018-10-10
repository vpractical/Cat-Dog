package com.y.config;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

public class UmConfig {

    private static final String APP_KEY = "5ba45307b465f553fd000068";

    public static void init(Application context){
        UMConfigure.init(context,APP_KEY,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setQQZone("1107733637", "oYuamU1NFwFxXePd");
        PlatformConfig.setDing("suiteiqxfvr2pevqi3ttv");
        PlatformConfig.setWeixin("wx8356bf447ddde8a2", "8e0a4f8db01e8d24cf5fdcc8ded328e8");

        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setAlipay("2015111700822536");

        /**
         * 设置每次调起授权页
         */
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(context).setShareConfig(config);
    }
}
