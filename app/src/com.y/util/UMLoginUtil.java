package com.y.util;

import android.app.Activity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.y.bean.User;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 第三方登录
 */
public class UMLoginUtil {
    private static final String TAG = "UMLoginUtil";

    /**
     * 第三方登录结果的回调
     */
    public interface UmLoginCallback {
        void onSuccess(User user);

        void onError();

        void onCancel();
    }

    private UmLoginCallback callback;
    private Activity activity;
    private UMShareAPI mShareAPI;
    private SHARE_MEDIA platform;
    private User mUser;

    public UMLoginUtil(Activity activity) {
        this.activity = activity;
        mShareAPI = UMShareAPI.get(activity);
    }

    public UMLoginUtil sina() {
        this.platform = SHARE_MEDIA.SINA;
        return this;
    }

    public UMLoginUtil qq() {
        this.platform = SHARE_MEDIA.QQ;
        return this;
    }

    public UMLoginUtil wx() {
        this.platform = SHARE_MEDIA.WEIXIN;
        return this;
    }

    public UMLoginUtil callback(UmLoginCallback c) {
        this.callback = c;
        return this;
    }

    public void login() {
        mShareAPI.getPlatformInfo(activity, platform, umAuthListener);
    }

    /**
     * 授权回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            try {
                L.e(TAG, "获取信息---------" + data.toString());
                mUser = new User();
                mUser.nick = data.get("screen_name");
                mUser.avatar = data.get("profile_image_url");
                mUser.sex = sex(data.get("gender"));
                mUser.access_token = data.get("access_token");

                if (platform == SHARE_MEDIA.WEIXIN) {
                    mUser.id = data.get("openid");
                } else {
                    mUser.id = data.get("uid");
                }

                if (platform == SHARE_MEDIA.SINA) {
                    mUser.city = data.get("location");
                    if (mUser.city.contains("北京")) {
                        mUser.city = "北京";
                    } else {
                        mUser.city = "";
                    }
                } else {
                    if (data.get("province").equals("北京")) {
                        mUser.city = "北京";
                    } else {
                        mUser.city = data.get("city");
                    }
                }
                thirdLogin();
            } catch (Exception e) {
                e.printStackTrace();
                T.show("第三方登录授权结果处理异常");
                L.e(TAG, "第三方登录授权结果处理异常  获取信息异常" + action);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (callback != null) {
                callback.onError();
            }
            L.e(TAG, "获取信息失败" + action + t.getMessage());
            String info = t.getMessage();
            if (info.contains("2008")) {
                if (platform == SHARE_MEDIA.QQ) {
                    T.show("没有安装正式版QQ");
                } else if (platform == SHARE_MEDIA.WEIXIN) {
                    T.show("没有安装微信");
                }
            } else {
                T.show("获取信息失败,请重试");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            L.e(TAG, "获取信息取消");
            if (callback != null) {
                callback.onCancel();
            }
        }
    };

    private void thirdLogin() {
        if (platform == SHARE_MEDIA.SINA) {
            mUser.platform_sina();
        } else if (platform == SHARE_MEDIA.QQ) {
            mUser.platform_qq();
        } else if (platform == SHARE_MEDIA.WEIXIN) {
            mUser.platform_wx();
        }


        Observable
                .timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                    }


                    @Override
                    public void onError(Throwable e) {
                        if (callback != null && !activity.isFinishing()) {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (callback != null && !activity.isFinishing()) {
                            callback.onSuccess(mUser);
                        }
                    }
                });

    }


    private int sex(String gender) {
        if (gender == null || gender.equals("")) {
            return 0;
        } else if (gender.equals("1") || gender.equals("男")) {
            return 1;
        } else {
            return 2;
        }
    }
}
