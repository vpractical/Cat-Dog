package com.y.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.io.File;

/**
 * @author pc
 */
public class ILBuilder implements Cloneable{
    //图片宽高
    public int targetWidth, targetHeight;
    //占位、出错占位图
    public int placeHolder, errorHolder;
    //圆角，旋转度数
    public float angle, rotate;
    //圆形
    public boolean circle;
    //缩放显示策略
    public ImageView.ScaleType scaleType;
    //是否缓存
    public boolean skipMemoryCache, skipDiskCache;
    //是否允许图片动画
    public boolean animate;
    //图片质量
    public Bitmap.Config config = Bitmap.Config.RGB_565;
    //待显示的image
    public File targetFile;
    public int targetResource;
    public String targetUrl;
    public Uri targetUri;
    public Drawable targetDrawable;
    public Bitmap targetBitmap;
    //上下文
    public Activity activity;
    public Fragment fragment;
    public Context context;
    //图片加载回调
    public ILCallback.BitmapCallback bitmapCallback;
    public ILCallback.ProgressCallback progressCallback;
    //待显示的view
    public ImageView targetView;

    //加载为gif、bitmap
    public boolean asGif,asBitmap = true;

    //是否倒叙加载
    public boolean reverse;
    //为加载设置tag，用于暂停、取消加载
    public String pauseTag,cancelTag;

    //临时使用的加载策略
    public ILStrategy loaderImpl;

    public ILBuilder(){

    }

    public ILBuilder(Activity activity) {
        this.activity = activity;
    }

    public ILBuilder(Fragment fragment) {
        this.fragment = fragment;
    }

    public ILBuilder(Context context) {
        this.context = context;
    }

    public ILBuilder with(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ILBuilder with(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public ILBuilder with(Context context) {
        this.context = context;
        return this;
    }

    public ILBuilder targetWidth(int width) {
        this.targetWidth = width;
        return this;
    }

    public ILBuilder targetHeight(int height) {
        this.targetHeight = height;
        return this;
    }

    public ILBuilder placeHolder(int holder) {
        this.placeHolder = holder;
        return this;
    }

    public ILBuilder errorHolder(int holder) {
        this.errorHolder = holder;
        return this;
    }

    public ILBuilder angle(float angle) {
        this.angle = angle;
        return this;
    }

    public ILBuilder rotate(float rotate) {
        this.rotate = rotate;
        return this;
    }

    public ILBuilder circle(){
        this.circle = true;
        return this;
    }

    public ILBuilder asGif(){
        this.asGif = true;
        this.asBitmap = false;
        return this;
    }

    public ILBuilder scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public ILBuilder skipMemoryCache(boolean skip) {
        this.skipMemoryCache = skip;
        return this;
    }

    public ILBuilder skipDiskCache(boolean skip) {
        this.skipDiskCache = skip;
        return this;
    }

    public ILBuilder animate(boolean animate) {
        this.animate = animate;
        return this;
    }

    public ILBuilder config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public ILBuilder reverse(boolean reverse){
        this.reverse = reverse;
        return this;
    }

    public ILBuilder pauseTag(String tag){
        this.pauseTag = tag;
        return this;
    }

    public ILBuilder cancelTag(String tag){
        this.cancelTag = tag;
        return this;
    }

    public ILBuilder callback(ILCallback.ProgressCallback callback) {
        this.progressCallback = callback;
        return this;
    }

    public ILBuilder url(File file) {
        this.targetFile = file;
        return this;
    }

    public ILBuilder url(int resource) {
        this.targetResource = resource;
        return this;
    }

    public ILBuilder url(String url) {
        this.targetUrl = url;
        return this;
    }

    public ILBuilder url(Uri uri) {
        this.targetUri = uri;
        return this;
    }

    public ILBuilder url(Drawable drawable) {
        this.targetDrawable = drawable;
        return this;
    }

    public ILBuilder url(Bitmap bitmap) {
        this.targetBitmap = bitmap;
        return this;
    }

    /**
     * 开始加载
     */
    public void into(ImageView targetView) {
        this.targetView = targetView;
        ImageLoader.getInstance().build(this);
    }

    /**
     * 开始转为bitmap
     */
    public void toBitmap(ILCallback.BitmapCallback callback) {
        this.bitmapCallback = callback;
        ImageLoader.getInstance().build(this);
    }


    /**
     * 偶尔几张图片使用不用的加载实现
     */
    public ILBuilder strategy(ILStrategy strategy){
        this.loaderImpl = strategy;
        return this;
    }

    @Override
    public ILBuilder clone(){
        try {
            ILBuilder builder = (ILBuilder) super.clone();
            return builder;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
