package com.y.mvp.converter;

public class BaseRes {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "null" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
