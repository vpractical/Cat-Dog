package com.y.config;

import java.util.HashMap;
import java.util.Map;


public class WeatherConfig {

    /**
     * 天气api的appKey
     */
    public static final String APPKEY_WEATHER = "23822036";
    public static final String APPSECRET_WEATHER = "c90795175968657d40c8a3becbd36ff0";
    public static final String APPCODE_WEATHER = "207fee5d19654432a9cf0b05f77824f0";

    /**
     * 天气和天气图标的映射
     */
    private static Map<String,Integer> mapping = new HashMap<>();

    public static int getMapping(String weather){
        if(mapping.containsKey(weather)){
            return mapping.get(weather);
        }
        return 0;
    }

    static {
        mapping.put("晴",0);
        mapping.put("多云",1);
        mapping.put("阴",2);
        mapping.put("阵雨",3);
        mapping.put("雷阵雨",4);
        mapping.put("雷阵雨伴有冰雹",5);
        mapping.put("雨夹雪",6);
        mapping.put("小雨",7);
        mapping.put("中雨",8);
        mapping.put("大雨",9);
        mapping.put("暴雨",10);
        mapping.put("大暴雨",11);
        mapping.put("特大暴雨",12);
        mapping.put("阵雪",13);
        mapping.put("小雪",14);
        mapping.put("中雪",15);
        mapping.put("大雪",16);
        mapping.put("暴雪",17);
        mapping.put("雾",18);
        mapping.put("冻雨",19);
        mapping.put("沙尘暴",20);
        mapping.put("小雨-中雨",21);
        mapping.put("中雨-大雨",22);
        mapping.put("大雨-暴雨",23);
        mapping.put("暴雨-大暴雨",24);
        mapping.put("大暴雨-特大暴",25);
        mapping.put("小雪-中雪",26);
        mapping.put("中雪-大雪",27);
        mapping.put("大雪-暴雪",28);
        mapping.put("浮尘",29);
        mapping.put("扬沙",30);
        mapping.put("强沙尘暴",31);
        mapping.put("浓雾",32);
        mapping.put("强浓雾",49);
        mapping.put("霾",53);
        mapping.put("中度霾",54);
        mapping.put("重度霾",55);
        mapping.put("严重霾",56);
        mapping.put("大雾",57);
        mapping.put("特强浓雾",58);
        mapping.put("无",99);
        mapping.put("雨",301);
        mapping.put("雪",302);
    }
}
