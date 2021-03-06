package com.y.api;

import com.y.config.Const;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SystemApi {

    Retrofit mRetrofit;

//    @Inject
//    LoadingDialog mLoadingDialog;


    @Inject
    public SystemApi(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public CommonSubscriber<String> checkUpdate(CommonSubscriber<String> subscriber) {
        return mRetrofit.create(ApiService.class)
                .checkUpdate(Const.VERSION_CHECK_URL)
                .compose(Transformer.<String>switchSchedulers())
                .subscribeWith(subscriber);
    }

}
