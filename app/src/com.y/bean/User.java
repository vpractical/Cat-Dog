package com.y.bean;

/**
 * 登录用户
 */
public class User {
    public String access_token;

    public String id,nick,phone,avatar;

    public String address,city;

    /**
     * sex:1男2女
     */
    public int sex,age;

    /**
     * 用户平台，0:自有,1:qq,2:wx,3:sina
     */
    public int platform;

    public void platform_qq(){
        platform = 1;
    }
    public void platform_wx(){
        platform = 2;
    }
    public void platform_sina(){
        platform = 3;
    }

}
