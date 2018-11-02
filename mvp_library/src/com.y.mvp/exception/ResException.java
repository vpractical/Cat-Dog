package com.y.mvp.exception;

public class ResException extends Exception {

    private int code;
    private String msg;

    public ResException(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        if(msg == null){
            return "";
        }
        return msg;
    }

}
