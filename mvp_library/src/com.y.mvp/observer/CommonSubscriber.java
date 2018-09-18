package com.y.mvp.observer;

import com.y.mvp.base.BaseView;
import com.y.mvp.exception.ApiException;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class CommonSubscriber<W> extends ResourceSubscriber<W> {

    private BaseView mView;
    protected CommonSubscriber() {
    }

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        String message = ApiException.handleException(t).getLocalizedMessage();
        onError(message);
        if (mView == null) {
            return;
        }
        mView.showError(message);
    }

    protected void onError(String mes) {

    }
}
