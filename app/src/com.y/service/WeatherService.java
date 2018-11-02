package com.y.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.y.api.ApiService;
import com.y.config.Const;
import com.y.config.WeatherConfig;
import com.y.util.L;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        check();
    }

    private void check(){
        Map<String,String> map = new HashMap<>();
        map.put("city","北京");

        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL).build();
        mRetrofit
                .create(ApiService.class)
                .checkWeather2(Const.WEATHER_URL,map,"APPCODE " + WeatherConfig.APPCODE_WEATHER)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        L.e("WeatherService", response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        L.e("WeatherService",t.getMessage());
                    }
                });
    }


}
