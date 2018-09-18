package com.y.mvp.util.persenter;

import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;

public class EmptyPresenter extends RxPresenter<EmptyContract.View> implements EmptyContract.Presenter {

    @Inject
    public EmptyPresenter(){

    }
}
