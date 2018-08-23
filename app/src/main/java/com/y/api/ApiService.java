package com.y.api;

import com.y.bean.Login;
import com.y.config.Const;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET(Const.VERSION_CHECK_URL)
    Flowable<String> checkUpdate();

    @FormUrlEncoded
    @POST(Const.LOGIN_URL)
    Flowable<Login> login(@Field("phone") String phone, @Field("code") String code);
}
