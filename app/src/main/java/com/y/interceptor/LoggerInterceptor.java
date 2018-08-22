package com.y.interceptor;

import com.y.util.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggerInterceptor implements Interceptor {
    private static final String TAG = "Http";
    private StringBuilder sb;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        sb = new StringBuilder();
        long t1 = System.nanoTime();
        sb.append(request.method()).append(";").append(request.url()).append(";");
        L.e(TAG, sb.toString());

        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        MediaType mediaType = body.contentType();

        if(mediaType != null && "octet-stream".equals(mediaType.subtype())){
            return chain.proceed(request);
        }

        sb = new StringBuilder();
        long t2 = System.nanoTime();
        String[] split = request.url().url().toString().split("/");
        String route = split[split.length - 1].split("\\?")[0];
        String content = body.string();
        sb.append(response.code()).append("; ")
                .append((int) ((t2 - t1) / 1e6)).append("ms; /")
                .append(split[split.length - 2]).append("/")
                .append(route).append(";\n")
                .append(content);
        L.e(TAG, sb.toString());
        return response.newBuilder()
                .body(ResponseBody.create(mediaType,content))
                .build();
    }
}
