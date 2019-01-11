package com.y.api;

import com.y.bean.response.RoleRes;
import com.y.config.Const;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class GameApi {

    Retrofit mRetrofit;

    @Inject
    LoadingDialog mLoadingDialog;

    @Inject
    public GameApi(Retrofit retrofit){
        this.mRetrofit = retrofit;
    }

    public ResourceSubscriber<ResponseBody> downloadRole(ResourceSubscriber<ResponseBody> subscriber){
        return mRetrofit.create(ApiService.class)
                .downloadRole(Const.GAME_ROLE_DOWNLOAD_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(subscriber);
    }

    public CommonSubscriber<RoleRes> getAllRole(CommonSubscriber<RoleRes> subscriber) {
        return mRetrofit.create(ApiService.class)
                .getAllRole(Const.GET_ROLE_FROM_SERVER_URL)
                .compose(Transformer.<RoleRes>switchSchedulers())
                .subscribeWith(subscriber);
    }
}
