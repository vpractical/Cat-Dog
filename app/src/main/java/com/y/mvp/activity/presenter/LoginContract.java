package com.y.mvp.activity.presenter;

import com.y.bean.Login;
import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public interface LoginContract {


    interface View extends BaseView {
        void LoginSuccess(Login login);
    }

    interface Presenter extends BasePresenter<View> {
        //登录
        void login(String phone,String code);
    }


}
