package com.y.api;

import com.y.bean.HotStraetgyEntity;
import com.y.config.Const;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class VideoApi {

    Retrofit mRetrofit;

    @Inject
    LoadingDialog mLoadingDialog;

    @Inject
    public VideoApi(Retrofit retrofit) {
        mRetrofit = retrofit;
    }


    public CommonSubscriber<HotStraetgyEntity> loadHot(CommonSubscriber<HotStraetgyEntity> subscriber) {
        return mRetrofit.create(ApiService.class)
                .loadVideoHot(Const.EYE_HOT_STRATEGY)
                .compose(Transformer.<HotStraetgyEntity>switchSchedulers())
                .subscribeWith(subscriber);
    }
}
