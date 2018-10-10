package com.y.mvp.activity.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;
import com.y.util.UMLoginUtil;

public interface LoginContract {


    interface View extends BaseView {
        void LoginSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        //登录
        void loginByCode(String phone,String code);
        void loginByPwd(String phone,String pwd);
        void loginByFace();
        void loginByVisit(String avatar,String phone,String nick);
        void loginByThird(UMLoginUtil umLogin);
    }
}
