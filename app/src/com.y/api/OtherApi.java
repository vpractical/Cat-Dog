package com.y.api;

import com.y.config.Const;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class OtherApi {

    Retrofit mRetrofit;

    @Inject
    public OtherApi(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public CommonSubscriber<String> checkWeather(Map<String,String> map, CommonSubscriber<String> subscriber) {
        mRetrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL).build();
        return mRetrofit.create(ApiService.class)
                .checkWeather(Const.WEATHER_URL,map)
                .compose(Transformer.<String>switchSchedulers())
                .subscribeWith(subscriber);
    }

}
