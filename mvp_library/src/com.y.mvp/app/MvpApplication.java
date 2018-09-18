package com.y.mvp.app;

import android.app.Application;

import com.y.mvp.component.AppComponent;
import com.y.mvp.component.DaggerAppComponent;
import com.y.mvp.module.ConfigModule;
import com.y.mvp.module.GlobalConfigBuild;

import java.util.List;

public class MvpApplication extends Application implements App {

    private AppComponent iAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //dagger 初始化
        initComponent();
    }

    private void initComponent() {
        iAppComponent = DaggerAppComponent
                .builder()
                //提供application
                .application(this)
                //全局配置
                .globalConfigModule(getGlobalConfigModule(new ManifestParser(this).parse()))
                .build();
    }


    /**
     * 设置全局参数  包括网络请求层
     */
    private static GlobalConfigBuild getGlobalConfigModule(List<ConfigModule> modules) {
        GlobalConfigBuild.Builder builder = GlobalConfigBuild
                .builder();
        for (ConfigModule module : modules) {
            // 给全局配置 GlobalConfigBuild 添加参数
            module.applyOptions(builder);
        }
        return builder.build();
    }

    @Override
    public AppComponent getAppComponent() {
        return iAppComponent;
    }
}
