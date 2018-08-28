package com.y.config;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.y.imageloader.ILBuilder;
import com.y.imageloader.ILStrategy;

public class GlideConfiguration implements ILStrategy {
    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void clearDiskCache() {

    }

    @Override
    public void build(ILBuilder builder) {
        RequestOptions op = new RequestOptions();

        if(builder.placeHolder > 0){
            op = op.placeholder(builder.placeHolder);
        }

        if(builder.errorHolder > 0){
            op = op.error(builder.errorHolder);
        }

        if(builder.circle){
            op = op.circleCrop();
        }

        RequestManager rm;
        if(builder.activity != null){
            rm = Glide.with(builder.activity);
        }else if(builder.fragment != null){
            rm = Glide.with(builder.fragment);
        }else if(builder.context != null){
            rm = Glide.with(builder.context);
        }else {
            throw new NullPointerException("glide.with(?)");
        }

        RequestBuilder rb;
        if(builder.targetUrl != null){
            rb = rm.load(builder.targetUrl);
        }else if(builder.targetResource > 0){
            rb = rm.load(builder.targetResource);
        }else if(builder.targetBitmap != null){
            rb = rm.load(builder.targetBitmap);
        }else if(builder.targetDrawable != null){
            rb = rm.load(builder.targetDrawable);
        }else if(builder.targetFile != null){
            rb = rm.load(builder.targetFile);
        }else if(builder.targetUri != null){
            rb = rm.load(builder.targetUri);
        }else{
            throw new NullPointerException("glide.load(?)");
        }

        rb = rb.apply(op);

        if(builder.targetView != null){
            rb.into(builder.targetView);
        }else{
            throw new NullPointerException("glide.into(?)");
        }

    }

    @Override
    public void pause(String tag) {

    }

    @Override
    public void cancel(String tag) {

    }
}
