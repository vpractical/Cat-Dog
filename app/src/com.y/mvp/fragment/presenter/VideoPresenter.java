package com.y.mvp.fragment.presenter;

import com.y.api.VideoApi;
import com.y.bean.HotStraetgyEntity;
import com.y.mvp.base.RxPresenter;
import com.y.mvp.observer.CommonSubscriber;

import javax.inject.Inject;

public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {

    @Inject
    VideoApi mVideoApi;

    @Inject
    public VideoPresenter(){

    }

    @Override
    public void load(final int page) {
        mVideoApi.loadHot(new CommonSubscriber<HotStraetgyEntity>(mView) {

            @Override
            public void onSuccess(HotStraetgyEntity entity) {
                mView.loadSuccess(page,entity.getItemList());
            }

            @Override
            public void onFailed(String msg) {
                mView.loadFailed(page,msg);
            }
        });
    }
}
