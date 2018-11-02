package com.y.mvp.module;

import android.text.TextUtils;

import com.y.mvp.scope.InterceptorsScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

@Module
public class GlobalConfigBuild {
    private HttpUrl baseUrl;
    private List<Interceptor> interceptors;
    private List<Interceptor> netInterceptors;
    private AppModule.IGsonConfiguration iGsonConfiguration;
    private ClientModule.IRetrofitConfiguration iRetrofitConfiguration;
    private ClientModule.IOkHttpConfiguration iOkHttpConfiguration;

    private GlobalConfigBuild(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.interceptors = builder.interceptors;
        this.netInterceptors = builder.netInterceptors;
        this.iGsonConfiguration = builder.iGsonConfiguration;
        this.iRetrofitConfiguration = builder.iRetrofitConfiguration;
        this.iOkHttpConfiguration = builder.iOkHttpConfiguration;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
    @Nullable
    @InterceptorsScope("Interceptors")
    List<Interceptor> provideInterceptors() {
        return interceptors;
    }

    @Singleton
    @Provides
    @Nullable
    @InterceptorsScope("netInterceptors")
    List<Interceptor> provideNetInterceptors() {
        return netInterceptors;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return baseUrl;
    }

    @Singleton
    @Provides
    @Nullable
    AppModule.IGsonConfiguration provideGsonConfiguration() {
        return iGsonConfiguration;
    }

    @Singleton
    @Provides
    ClientModule.IRetrofitConfiguration provideRetrofitConfiguration() {
        return iRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.IOkHttpConfiguration provideOkHttpConfiguration() {
        return iOkHttpConfiguration;
    }


    public static final class Builder {
        private HttpUrl baseUrl;
        private List<Interceptor> interceptors = new ArrayList<>();
        private List<Interceptor> netInterceptors = new ArrayList<>();
        private AppModule.IGsonConfiguration iGsonConfiguration;
        private ClientModule.IRetrofitConfiguration iRetrofitConfiguration;
        private ClientModule.IOkHttpConfiguration iOkHttpConfiguration;

        private Builder() {
        }

        public Builder baseUrl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }


        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            this.netInterceptors.add(interceptor);
            return this;
        }

        public Builder gsonConfiguration(AppModule.IGsonConfiguration gsonConfiguration) {
            this.iGsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder retrofitConfiguration(ClientModule.IRetrofitConfiguration retrofitConfiguration) {
            this.iRetrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okHttpConfiguration(ClientModule.IOkHttpConfiguration okHttpConfiguration) {
            this.iOkHttpConfiguration = okHttpConfiguration;
            return this;
        }

        public GlobalConfigBuild build() {
            return new GlobalConfigBuild(this);
        }
    }
}
