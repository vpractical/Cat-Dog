package com.y.bean;

import com.y.config.Key;
import com.y.util.SPUtil;

/**
 * 登录用户
 */
public class User {

    public String nick,phone,avatar;

    public String address;

    /**
     * sex:1男2女
     */
    public int sex,age;

    public static void exit(){
        SPUtil.deleteSingleObject(Key.USER_KEY);
    }
}
