package com.y.api;

import com.google.gson.Gson;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SystemApi {

    Retrofit mRetrofit;

    @Inject
    LoadingDialog mLoadingDialog;

    Gson mGson;

    @Inject
    public SystemApi(Retrofit retrofit, Gson gson) {
        mRetrofit = retrofit;
        mGson = gson;
    }

    public CommonSubscriber<String> checkUpdate(CommonSubscriber<String> subscriber) {
        return mRetrofit.create(ApiService.class)
                .checkUpdate()
                .compose(Transformer.<String>switchSchedulers())
                .subscribeWith(subscriber);
    }

}
