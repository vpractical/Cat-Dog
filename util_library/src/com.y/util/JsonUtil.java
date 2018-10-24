package com.y.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据解析工具类
 */
public class JsonUtil {
    /**
     * 将json数据解析成给定的bean类对象
     *
     * @param json     json数据
     * @param classOfT 需要生成的bean类的字节码文件
     * @param <T>      需要生成的bean类
     * @return 生成的bean类对象
     */
    public static <T> T toBean(String json, Class<T> classOfT) throws JsonSyntaxException {
        return JSON.parseObject(json, classOfT);
    }

    public static <T> List<T> toList(String json, Class<T> classOfT) throws JsonSyntaxException {
        return JSON.parseArray(json, classOfT);
    }

    /**
     * -str
     */
    public static String toStr(Object o) {
        return JSON.toJSONString(o);
    }

    /**
     * str-jsonObject
     */
    public static JSONObject toJson(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    /**
     * 对象-jsonObject
     */
    public static JSONObject toJsonObject(Object object) {
        return (JSONObject) JSON.toJSON(object);
    }

    /**
     * 对象-jsonObject
     */
    public static JSONArray toJsonArray(Object object) {
        return (JSONArray) JSON.toJSON(object);
    }

    /**
     * 对象-map
     */
    public static Map<String, String> toMap(Object object) {
        JSONObject jsonObject = toJsonObject(object);
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }
}
