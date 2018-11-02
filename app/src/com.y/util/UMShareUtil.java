package com.y.util;

import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

/**
 * 第三方分享
 * <p>
 * ywb 16.11.15
 * <p>
 * 图片 文字 链接 音乐 视频 文件
 * <p>
 * 1 图片 音乐 视频 只能同时分享其中一个
 * 2 .
 * <p>
 * 17.4.7,um  sdk升级
 */

public class UMShareUtil {
    private Activity activity;
    private ShareAction shareAction;
    private UMWeb umWeb;
    private UMImage umImage;
    private String title = "黄昏", content = "黄昏";
    private UMShareListener umShareListener;

    public UMShareUtil(Activity activity) {
        this.activity = activity;
        shareAction = new ShareAction(activity);
    }

    /**
     * 分享平台
     */
    public UMShareUtil wx(){
        shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
        return this;
    }

    public UMShareUtil wx_circle(){
        shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        return this;
    }

    public UMShareUtil wx_favor(){
        shareAction.setPlatform(SHARE_MEDIA.WEIXIN_FAVORITE);
        return this;
    }

    public UMShareUtil qq(){
        shareAction.setPlatform(SHARE_MEDIA.QQ);
        return this;
    }

    public UMShareUtil qzone(){
        shareAction.setPlatform(SHARE_MEDIA.QZONE);
        return this;
    }

    public UMShareUtil ali(){
        shareAction.setPlatform(SHARE_MEDIA.ALIPAY);
        return this;
    }

    public UMShareUtil ding(){
        shareAction.setPlatform(SHARE_MEDIA.DINGTALK);
        return this;
    }

    public UMShareUtil sina(){
        shareAction.setPlatform(SHARE_MEDIA.SINA);
        return this;
    }

    public UMShareUtil sms(){
        shareAction.setPlatform(SHARE_MEDIA.SMS);
        return this;
    }

    public UMShareUtil email(){
        shareAction.setPlatform(SHARE_MEDIA.EMAIL);
        return this;
    }


    public UMShareUtil title(String title) {
        this.title = title == null || title.equals("") ? this.title : title;
        if (umWeb != null)
            umWeb.setTitle(this.title);
        return this;
    }

    public UMShareUtil image(int imgId) {
        this.umImage = new UMImage(activity, imgId);
        if (umWeb != null)
            umWeb.setThumb(this.umImage);
        return this;
    }

    public UMShareUtil image(String url) {
        this.umImage = new UMImage(activity, url);
        if (umWeb != null)
            umWeb.setThumb(this.umImage);
        return this;
    }

    public UMShareUtil image(Bitmap bitmap) {
        this.umImage = new UMImage(activity, bitmap);
        if (umWeb != null)
            umWeb.setThumb(this.umImage);
        return this;
    }

    public UMShareUtil url(String url) {
        url = url == null ? "" : url;
        umWeb = new UMWeb(url);
        title(this.title);
        text(this.content);
        if (this.umImage != null)
            umWeb.setThumb(this.umImage);
        return this;
    }

    public UMShareUtil text(String text) {
        this.content = text == null || text.equals("") ? this.content : text;
        if (umWeb != null)
            umWeb.setDescription(this.content);
        return this;
    }

    public UMShareUtil file(File file) {
        shareAction.withFile(file);
        return this;
    }

    public UMShareUtil callBack(UMShareListener listener) {
        this.umShareListener = listener;
        shareAction.setCallback(umListener);
        return this;
    }

    public void share() {
        if (umWeb == null) {
            L.e("分享链接是null");
            return;
        }
        shareAction.withMedia(umWeb);
        shareAction.share();
    }


    private UMShareListener umListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            if (umShareListener != null) {
                umShareListener.onStart(share_media);
            }
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            L.e("share成功 " + platform.name());
            if (umShareListener != null) {
                umShareListener.onResult(platform);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, final Throwable t) {
            L.e("分享失败 " + platform + "\n" + t.toString());
            if (!activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (t.getMessage().contains("没有安装应用")) {
                            T.show("本机暂未安装该应用");
                        } else {
                            T.show(t.toString());
                        }
                    }
                });
            }
            if (umShareListener != null) {
                umShareListener.onError(platform, t);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            L.e("分享取消 " + platform.name());
            if (umShareListener != null) {
                umShareListener.onCancel(platform);
            }
        }
    };

}
