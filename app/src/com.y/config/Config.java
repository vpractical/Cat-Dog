package com.y.config;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Config {

    /**
     * 登录方式
     */
    public static List<Pair<Integer,String>> loginTypes(){
        List<Pair<Integer,String>> types = new ArrayList<>();
        types.add(new Pair<>(0,"验证码"));
        types.add(new Pair<>(1,"密码"));
        types.add(new Pair<>(2,"刷脸"));
        types.add(new Pair<>(3,"游客"));
        return types;
    }
}
