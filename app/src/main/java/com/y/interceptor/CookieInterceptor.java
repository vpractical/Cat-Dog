package com.y.interceptor;


import com.y.config.Key;
import com.y.util.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        if(SPUtil.getCommonString(Key.NET_COOKIES) != null){
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Cookie",SPUtil.getCommonString(Key.NET_COOKIES));
        }

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            if (chain.request().url().toString().equals("77777777777777")) {
                SPUtil.putCommonString(Key.NET_COOKIES, cookieBuffer.toString());
            }
        }
        return originalResponse;
    }
}