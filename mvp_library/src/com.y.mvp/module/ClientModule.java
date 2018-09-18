package com.y.mvp.module;

import com.y.mvp.scope.InterceptorsScope;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ClientModule {

    private static final int DEFAULT_TIME_OUT = 15;

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(Retrofit.Builder builder, IRetrofitConfiguration iRetrofitConfiguration, OkHttpClient okHttpClient, HttpUrl url){
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .client(okHttpClient);
        if (iRetrofitConfiguration != null) {
            iRetrofitConfiguration.configRetrofit(builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient provideClient(OkHttpClient.Builder builder, @Nullable IOkHttpConfiguration iOkHttpConfiguration,
                                      @Nullable @InterceptorsScope("Interceptors") List<Interceptor> interceptors,
                                      @Nullable @InterceptorsScope("netInterceptors") List<Interceptor> netInterceptors){
        //如果外部提供了interceptor的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (netInterceptors != null) {
            for (Interceptor interceptor : netInterceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        if (iOkHttpConfiguration != null) {
            iOkHttpConfiguration.configOkHttp(builder);
        }
        return builder.build();
    }


    public interface IRetrofitConfiguration {
        void configRetrofit(Retrofit.Builder builder);
    }

    public interface IOkHttpConfiguration {
        void configOkHttp(OkHttpClient.Builder builder);
    }
}
