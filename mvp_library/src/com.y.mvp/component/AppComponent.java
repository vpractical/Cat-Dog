package com.y.mvp.component;

import android.app.Application;

import com.google.gson.Gson;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.module.AppModule;
import com.y.mvp.module.ClientModule;
import com.y.mvp.module.GlobalConfigBuild;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * 汇集提供对象的module
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigBuild.class})
public interface AppComponent {

    Application application();

    LoadingDialog loadingDialog();

    Retrofit retrofit();

    Gson gson();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigBuild globalConfigModule);

        AppComponent build();
    }

}
