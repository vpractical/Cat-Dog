package com.y.config;

public class Const {
    public static final String BASE_URL = "http://www.baidu.com";

    /**
     * 立绘本地存储目录
     */
    public static final String PATH_GAME_ROLE_DIR = "/game/role/";

    /**
     * 立绘下载目录
     */
    public static final String GAME_ROLE_DOWNLOAD_URL = "http://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/a564a5c1cc4f69d62332a4b179a0f9eca69c7295134605804adc60c8f4907e35681ca6ef492aa709f13f732f520653e32862f93b9e5bea9521c8cadb9911a828/role.rar?fname=role.rar&from=30113&version=3.3.3.3&uin=10000";

    /**
     *  检查更新
     */
    public static final String VERSION_CHECK_URL = "http://wx.yuneke.com/usercentre/verController.do?&verNo=3.1.weather0.10-debug&model=TPS615&orgId=";
    /**
     *  登录
     */
    public static final String LOGIN_URL = "";


    public static final String WEATHER_URL = "http://jisutqybmf.market.alicloudapi.com/weather/query/";

    /**
     * 开眼视频接口
     */
    //每日精选
    public static final String EYE_DAILY="http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多
    public static final String EYE_FIND_MORE="http://baobab.wandoujia.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //热门排行
    public static final String EYE_HOT_STRATEGY="http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多详情接口
    public static final String EYE_FIND_DETAIL="http://baobab.wandoujia.com/api/v3/videos?categoryName=%s&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
}
