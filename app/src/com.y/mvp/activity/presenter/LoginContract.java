package com.y.mvp.activity.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;
import com.y.util.UMLoginUtil;

public interface LoginContract {


    interface View extends BaseView {
        void loginSuccess();
    }

    interface PwdView extends View{
        void registerFailed(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        //登录
        void loginByCode(String phone,String code);
        void loginByPwd(String name,String pwd);
        //忘记密码
        void forgetPwd();
        //账号密码注册
        void registerPwd(String name,String pwd);
        void loginByFace();
        void loginByVisit(String avatar,String phone,String nick);
        void loginByThird(UMLoginUtil umLogin);
    }
}
