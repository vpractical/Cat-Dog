package com.y.config;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SystemConfig {

    public static final int LOGIN_BY_CODE = 0;
    public static final int LOGIN_BY_PWD = 1;
    public static final int LOGIN_BY_FACE = 2;
    public static final int LOGIN_BY_THIRD = 3;
    public static final int LOGIN_BY_VISIT = 4;

    /**
     * 登录方式
     */
    public static List<Pair<Integer,String>> loginTypes(){
        List<Pair<Integer,String>> types = new ArrayList<>();
        types.add(new Pair<>(LOGIN_BY_CODE,"验证码"));
        types.add(new Pair<>(LOGIN_BY_PWD,"密码"));
        types.add(new Pair<>(LOGIN_BY_FACE,"刷脸"));
        types.add(new Pair<>(LOGIN_BY_THIRD,"三方"));
        types.add(new Pair<>(LOGIN_BY_VISIT,"游客"));
        return types;
    }
}
