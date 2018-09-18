package com.y.util;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

/**
 * 数据解析工具类
 */
public class JsonUtil {
    public static Gson gson;

    /**
     * 将json数据解析成给定的bean类对象
     *
     * @param json     json数据
     * @param classOfT 需要生成的bean类的字节码文件
     * @param <T>      需要生成的bean类
     * @return 生成的bean类对象
     */
    public static <T> T parseObject(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(json, classOfT);
    }


    public static <T> List<T> parseArray(String json, Class<T> classOfT) throws JsonSyntaxException {
        return JSON.parseArray(json, classOfT);
    }

    public static String toJson(Object o) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(o);
    }

}
