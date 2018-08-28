package com.y.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author pc
 */
public class ImageLoader {
    private static ImageLoader instance;
    private static ILStrategy loaderImpl;
    private static ILBuilder builder;

    static {
        instance = new ImageLoader();
    }

    public static ImageLoader getInstance(){
        return instance;
    }

    public ImageLoader strategy(ILStrategy impl){
        loaderImpl = impl;
        return this;
    }

    /**
     * 配置默认全局属性
     */
    public ILBuilder configure(){
        if(builder == null){
            builder = new ILBuilder();
        }
        return builder;
    }

    public void build(ILBuilder builder){
        if(builder.loaderImpl != null){
            builder.loaderImpl.build(builder);
        }else{
            checkNull();
            loaderImpl.build(builder);
        }
    }

    public static ILBuilder with(Activity activity){
        if(builder != null){
            return builder.clone().with(activity);
        }
        return new ILBuilder(activity);
    }
    public static ILBuilder with(Fragment fragment){
        if(builder != null){
            return builder.clone().with(fragment);
        }
        return new ILBuilder(fragment);
    }
    public static ILBuilder with(Context context){
        if(builder != null){
            return builder.clone().with(context);
        }
        return new ILBuilder(context);
    }

    public void clearMemoryCache(){
        checkNull();
        loaderImpl.clearMemoryCache();
    }

    public void clearDiskCache(){
        checkNull();
        loaderImpl.clearDiskCache();
    }

    private void checkNull(){
        if(loaderImpl == null){
            throw new NullPointerException("the implements of the ImageLoader is null");
        }
    }
}
