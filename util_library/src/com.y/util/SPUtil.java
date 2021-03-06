package com.y.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * common ：保存在一个xml中的字段
 * single ：单独保存在一个xml中
 */
public class SPUtil {

    private static final String COMMON_FILE_NAME = "cat_dog_value";

    public static void putCommonString(String key, String value) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getCommonString(String key) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static void putCommonInt(String key,int val){
        putCommonString(key,String.valueOf(val));
    }

    public static int getCommonInt(String key,int errorValue){
        String val = getCommonString(key);
        if(TextUtils.isEmpty(val)){
            return errorValue;
        }else{
            return Integer.parseInt(val);
        }
    }

    public static void deleteCommonString(String key) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, null).apply();
    }

    public static void deleteCommonString() {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void putSingleString(String fileName, String value) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putString("value", value).apply();
    }

    public static String getSingleString(String fileName) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString("value", null);
    }

    public static void deleteSingleString(String fileName) {
        SharedPreferences sp = AppUtil.context().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void putSingleObject(String fileName, Object obj) {
        String toSaveString;
        try {
            toSaveString = (obj == null) ? null : JsonUtil.toStr(obj);
        } catch (Exception e) {
            toSaveString = null;
        }
        putSingleString(fileName, toSaveString);
    }

    public static Object getSingleObject( String fileName, Class<?> clz) {
        String stringData = getSingleString(fileName);
        if (stringData == null)
            return null;
        if (!stringData.startsWith("{") && !stringData.startsWith("[")) {
            putSingleString(fileName, null);
            return null;
        }
        if (stringData.startsWith("{")) {
            try {
                return JsonUtil.toBean(stringData,clz);
            } catch (Exception e) {
                putSingleString(fileName, null);
                return null;
            }
        } else {
            try {
                return JsonUtil.toList(stringData, clz);
            } catch (Exception e) {
                putSingleString(fileName, null);
                return null;
            }
        }
    }

    public static void deleteSingleObject(String fileName){
        deleteSingleString(fileName);
    }


}
