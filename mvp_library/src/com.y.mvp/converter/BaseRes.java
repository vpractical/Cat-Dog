package com.y.mvp.converter;

public class BaseRes {

    public int code;
    private String msg;

    public String getMsg() {
        return msg == null ? "null" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
