package com.y.mvp.activity.presenter;


import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;


public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {


    @Inject
    public VideoPresenter() {
    }

}