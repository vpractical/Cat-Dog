package com.y.api;

import com.y.bean.Login;
import com.y.config.Const;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}
