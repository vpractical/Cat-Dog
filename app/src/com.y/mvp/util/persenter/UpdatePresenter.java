package com.y.mvp.util.persenter;

import com.y.api.SystemApi;
import com.y.mvp.base.RxPresenter;
import com.y.mvp.observer.CommonSubscriber;
import com.y.util.L;
import com.y.util.T;

import javax.inject.Inject;

public class UpdatePresenter extends RxPresenter<UpdateContract.View> implements UpdateContract.Presenter {
    SystemApi mSystemApi;

    @Inject
    public UpdatePresenter(SystemApi systemApi){
        this.mSystemApi = systemApi;
    }

    @Override
    public void checkUpdate() {
        mSystemApi.checkUpdate(new CommonSubscriber<String>() {
            @Override
            public void onNext(String res) {
                T.show(res);
                L.e(res);
            }
        });
    }
}
