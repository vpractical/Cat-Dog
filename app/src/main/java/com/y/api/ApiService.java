package com.y.api;

import com.y.config.Const;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface ApiService {

    @GET(Const.VERSION_CHECK)
    Flowable<String> checkUpdate();

}
