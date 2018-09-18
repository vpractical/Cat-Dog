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
        mVideoApi.loadHot(new CommonSubscriber<HotStraetgyEntity>() {
            @Override
            public void onNext(HotStraetgyEntity entity) {
                mView.loadSuccess(page,entity.getItemList());
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                mView.loadFailed(page,t.getLocalizedMessage());
            }
        });
    }
}
