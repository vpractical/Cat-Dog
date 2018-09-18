package com.y.mvp.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.y.mvp.base.LoadingDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable IGsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    public interface IGsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

    @Singleton
    @Provides
    static LoadingDialog provideDialog(Application application) {
        LoadingDialog lpDialog = new LoadingDialog(application);
        return lpDialog;
    }

}
