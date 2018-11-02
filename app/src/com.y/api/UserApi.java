package com.y.api;

import com.y.bean.request.LoginReq;
import com.y.bean.request.RegisterReq;
import com.y.bean.response.LoginRes;
import com.y.bean.response.RegisterRes;
import com.y.config.SystemConfig;
import com.y.mvp.base.LoadingDialog;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.observer.Transformer;
import com.y.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class UserApi {

    Retrofit mRetrofit;

    @Inject
    LoadingDialog mLoadingDialog;

    @Inject
    public UserApi(Retrofit retrofit) {
        mRetrofit = retrofit;
    }


    public CommonSubscriber<LoginRes> loginByCode(String phone,String code,CommonSubscriber<LoginRes> subscriber) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("loginType",String.valueOf(SystemConfig.LOGIN_BY_CODE));
        return mRetrofit.create(ApiService.class)
                .login(map)
                .compose(Transformer.<LoginRes>switchSchedulers(mLoadingDialog))
                .subscribeWith(subscriber);
    }

    public CommonSubscriber<LoginRes> loginByPwd(LoginReq req, CommonSubscriber<LoginRes> subscriber) {
//        Map<String,String> map = new HashMap<>();
//        map.put("account",account);
//        map.put("password",pwd);
//        map.put("loginType",String.valueOf(SystemConfig.LOGIN_BY_PWD));
        return mRetrofit.create(ApiService.class)
                .login(JsonUtil.toMap(req))
                .compose(Transformer.<LoginRes>switchSchedulers(mLoadingDialog))
                .subscribeWith(subscriber);
    }


    public CommonSubscriber<RegisterRes> registerPwd(RegisterReq req, CommonSubscriber<RegisterRes> subscriber) {
//        Map<String,String> map = new HashMap<>();
//        map.put("name",name);
//        map.put("password",pwd);
//        map.put("registerType",String.valueOf(SystemConfig.LOGIN_BY_PWD));
        return mRetrofit.create(ApiService.class)
                .register(JsonUtil.toMap(req))
                .compose(Transformer.<RegisterRes>switchSchedulers(mLoadingDialog))
                .subscribeWith(subscriber);
    }
}
