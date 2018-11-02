package com.y.mvp.activity.presenter;


import com.y.api.UserApi;
import com.y.bean.Login;
import com.y.bean.User;
import com.y.bean.request.LoginReq;
import com.y.bean.request.RegisterReq;
import com.y.bean.response.LoginRes;
import com.y.bean.response.RegisterRes;
import com.y.config.Key;
import com.y.config.SystemConfig;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.base.RxPresenter;
import com.y.mvp.observer.CommonSubscriber;
import com.y.util.SPUtil;
import com.y.util.T;
import com.y.util.UMLoginUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    UserApi mUserApi;

    @Inject
    LoadingDialog mLoadingDialog;

    @Inject
    public LoginPresenter(UserApi userApi) {
        this.mUserApi = userApi;
    }

    @Override
    public void loginByCode(String phone, String code) {
        Login login = new Login();
        login.phone = phone;
        login.loginType = SystemConfig.LOGIN_BY_CODE;
        long cur = System.currentTimeMillis();
        login.loginTime = cur;
        login.validTime = cur + 1000L * 60 * 60 * 24 * 90;

        User user = new User();
        user.nick = "奈雅丽";
        user.avatar = "http://a.hiphotos.baidu.com/zhidao/pic/item/21a4462309f79052782f28490ff3d7ca7bcbd591.jpg";
        user.phone = phone;
        user.address = "次元";
        user.age = 100018;
        user.sex = 2;

        SPUtil.putSingleObject(Key.LOGIN_KEY, login);
        SPUtil.putSingleObject(Key.USER_KEY, user);

        mLoadingDialog.show();
        Observable
                .timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        mLoadingDialog.dismiss();
                        mView.loginSuccess();
                    }
                });
    }

    @Override
    public void loginByPwd(String account, String pwd) {
        LoginReq req = new LoginReq();
        req.account = account;
        req.password = pwd;
        req.loginType = SystemConfig.LOGIN_BY_PWD;
        addSubscribe(
                "loginByPwd",
                mUserApi.loginByPwd(req, new CommonSubscriber<LoginRes>(mView) {
                    @Override
                    public void onSuccess(LoginRes data) {
                        Login login = data.login;
                        User user = data.user;
                        SPUtil.putSingleObject(Key.LOGIN_KEY, login);
                        SPUtil.putSingleObject(Key.USER_KEY, user);
                        mView.loginSuccess();
                    }

                    @Override
                    public void onFailed(String msg) {
                        T.show(msg);
                    }
                })
        );
    }

    @Override
    public void forgetPwd() {

    }

    @Override
    public void registerPwd(final String account, final String pwd) {
        RegisterReq req = new RegisterReq();
        req.account = account;
        req.password = pwd;
        req.registerType = SystemConfig.LOGIN_BY_PWD;

        addSubscribe(
                "registerPwd",
                mUserApi.registerPwd(req, new CommonSubscriber<RegisterRes>(mView) {

                    @Override
                    public void onSuccess(RegisterRes res) {
                        T.show("注册成功");
                        loginByPwd(account, pwd);
                    }

                    @Override
                    public void onFailed(String msg) {
                        T.show(msg);
                    }

                })
        );
    }

    @Override
    public void loginByFace() {

    }

    @Override
    public void loginByVisit(String avatar, String phone, String nick) {
        Login login = new Login();
        login.phone = phone;
        long cur = System.currentTimeMillis();
        login.loginTime = cur;
        login.validTime = cur + 1000L * 60 * 60 * 24 * 90;

        User user = new User();
        user.nick = nick;
        user.avatar = avatar;
        user.phone = phone;
        user.address = "魔界";
        user.age = 10008;
        user.sex = 2;

        SPUtil.putSingleObject(Key.LOGIN_KEY, login);
        SPUtil.putSingleObject(Key.USER_KEY, user);

        mView.loginSuccess();
    }

    @Override
    public void loginByThird(UMLoginUtil umLogin) {
        umLogin
                .callback(new UMLoginUtil.UmLoginCallback() {
                    @Override
                    public void onSuccess(User user) {
                        Login login = new Login();
                        long cur = System.currentTimeMillis();
                        login.loginTime = cur;
                        login.validTime = cur + 1000L * 60 * 60 * 24 * 90;

                        SPUtil.putSingleObject(Key.LOGIN_KEY, login);
                        SPUtil.putSingleObject(Key.USER_KEY, user);
                        mView.loginSuccess();
                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onCancel() {

                    }
                })
                .login();

    }
}