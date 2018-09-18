package com.y.mvp.fragment.presenter;

import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;

public class GamePresenter extends RxPresenter<GameContract.View> implements GameContract.Presenter {

    @Inject
    public GamePresenter(){

    }


}
