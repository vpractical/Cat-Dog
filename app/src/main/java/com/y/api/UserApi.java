package com.y.api;

import com.y.bean.Login;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class UserApi {

    Retrofit mRetrofit;


    @Inject
    LoadingDialog mLoadingDialog;

    @Inject
    public UserApi(Retrofit retrofit) {
        mRetrofit = retrofit;
    }


    public CommonSubscriber<Login> login(String phone,String code,CommonSubscriber<Login> subscriber) {
        return mRetrofit.create(ApiService.class)
                .login(phone,code)
                .compose(Transformer.<Login>switchSchedulers(mLoadingDialog))
                .subscribeWith(subscriber);
    }
}
