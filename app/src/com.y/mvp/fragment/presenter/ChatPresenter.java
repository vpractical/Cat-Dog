package com.y.mvp.fragment.presenter;

import com.y.mvp.base.RxPresenter;

import javax.inject.Inject;

public class ChatPresenter extends RxPresenter<ChatContract.View> implements ChatContract.Presenter {

    @Inject
    public ChatPresenter(){

    }


}
