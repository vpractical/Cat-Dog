package com.y.mvp.activity.presenter;


import com.y.api.UserApi;
import com.y.bean.Login;
import com.y.mvp.base.RxPresenter;
import com.y.mvp.observer.CommonSubscriber;

import javax.inject.Inject;


public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    UserApi mUserApi;

    @Inject
    public LoginPresenter(UserApi userApi) {
        this.mUserApi = userApi;
    }

    @Override
    public void login(String phone, String code) {
        addSubscribe(
                "login",
                mUserApi.login(phone, code, new CommonSubscriber<Login>(mView) {
                    @Override
                    public void onNext(Login login) {

                    }
                })
        );
    }
}