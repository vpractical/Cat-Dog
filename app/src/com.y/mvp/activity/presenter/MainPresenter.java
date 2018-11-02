package com.y.mvp.activity.presenter;

import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(){

    }

}
