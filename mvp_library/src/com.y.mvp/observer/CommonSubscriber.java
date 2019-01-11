package com.y.mvp.observer;

import com.y.mvp.base.BaseView;
import com.y.mvp.converter.BaseRes;
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
    public void onNext(W w) {
        if (mView == null) return;
        if (w instanceof BaseRes) {
            BaseRes baseRes = (BaseRes) w;

            if (baseRes.code == 0) {
                //三方接口
                onSuccess(w);
                return;
            }

            if (baseRes.code == 200) {
                onSuccess(w);
            } else {
                onFailed(baseRes.getMsg());
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        if (mView == null) return;
        String message = ApiException.handleException(t).getLocalizedMessage();
        onFailed(message);
    }

    public abstract void onSuccess(W data);

    public abstract void onFailed(String msg);
}
