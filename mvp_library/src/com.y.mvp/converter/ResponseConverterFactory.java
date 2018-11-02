//package com.y.mvp.converter;
//
//import com.google.gson.Gson;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Retrofit;
//
//public class ResponseConverterFactory extends Converter.Factory {
//
//    public static ResponseConverterFactory create() {
//        return new ResponseConverterFactory(new Gson());
//    }
//
//    private final Gson gson;
//
//    private ResponseConverterFactory(Gson gson) {
//        this.gson = gson;
//    }
//
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        //返回我们自定义的Gson响应体变换器
//        return new GsonResponseBodyConverter<>(gson, type);
//    }
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        //返回我们自定义的Gson响应体变换器
//        return new GsonResponseBodyConverter<>(gson,type);
//    }
//}