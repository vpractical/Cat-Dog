package com.y.api;

import com.y.bean.HotStraetgyEntity;
import com.y.bean.Login;
import com.y.config.Const;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Flowable<String> checkUpdate(@Url String url);

    @FormUrlEncoded
    @POST(Const.LOGIN_URL)
    Flowable<Login> login(@Field("phone") String phone, @Field("code") String code);

    @Streaming
    @GET
    Flowable<ResponseBody> downloadRole(@Url String url);

    @GET
    Flowable<HotStraetgyEntity> loadVideoHot(@Url String url);

    @GET
    Flowable<String> checkWeather(@Url String url, @QueryMap Map<String,String> map);

    @GET
    Call<ResponseBody> checkWeather2(@Url String url, @QueryMap Map<String,String> map, @Header("Authorization") String appCode);
}
