package com.y.bean;

import com.y.config.Key;
import com.y.util.SPUtil;

public class Login {
    /**
     * 登录时间，登录有效截止时间
     */
    public long loginTime,validTime;
    /**
     * 登录手机号
     */
    public String loginPhone;

    /**
     * 登录类型
     * 1：验证码登录；2：密码登录；3刷脸登录；4游客登录
     */
    public int loginType;


    public static void exit(){
        Login login = (Login) SPUtil.getSingleObject(Key.LOGIN_KEY,Login.class);
        login.validTime = 0;
        SPUtil.putSingleObject(Key.LOGIN_KEY,login);
    }
}
