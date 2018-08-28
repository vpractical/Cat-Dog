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


    public static void exit(){
        Login login = (Login) SPUtil.getSingleObject(Key.LOGIN_KEY,Login.class);
        login.validTime = 0;
        SPUtil.putSingleObject(Key.LOGIN_KEY,login);
    }
}
