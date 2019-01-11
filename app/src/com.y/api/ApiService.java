package com.y.api;

import com.y.bean.HotStraetgyEntity;
import com.y.bean.response.LoginRes;
import com.y.bean.response.RegisterRes;
import com.y.bean.response.RoleRes;
import com.y.config.Const;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
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
    @POST(Const.LOGIN_USER_URL)
    Flowable<LoginRes> login(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST(Const.REGISTER_USER_URL)
    Flowable<RegisterRes> register(@FieldMap Map<String,String> map);

    @Streaming
    @GET
    Flowable<ResponseBody> downloadRole(@Url String url);

    @GET
    Flowable<HotStraetgyEntity> loadVideoHot(@Url String url);

    @GET
    Flowable<String> checkWeather(@Url String url, @QueryMap Map<String,String> map);

    @GET
    Call<ResponseBody> checkWeather2(@Url String url, @QueryMap Map<String,String> map, @Header("Authorization") String appCode);

    @GET
    Flowable<RoleRes> getAllRole(@Url String url);
}
