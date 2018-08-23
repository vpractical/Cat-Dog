package com.y.mvp.fragment.presenter;

import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {

    @Inject
    public NewsPresenter(){

    }


}
