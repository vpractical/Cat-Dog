package com.y.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * log日志工具类
 */

public class L {

    public static void e(String tag, String str) {
        Logger.t(tag).e(str);
    }

    public static void i(String tag, String str) {
        Logger.t(tag).i(str);
    }

    public static void w(String tag, String str) {
        Logger.t(tag).w(str);
    }

    public static void d(String tag, Object str) {
        Logger.t(tag).d(str);
    }

    public static void json(String tag, String json) {
        Logger.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        Logger.t(tag).xml(xml);
    }

    public static void e(String str) {
        Logger.e(str);
    }

    public static void i(String str) {
        Logger.i(str);
    }

    public static void w(String str) {
        Logger.w(str);
    }

    public static void d(Object str) {
        Logger.d(str);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    /**
     * ------------------------- Logger -------------------------------
     */
    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息
                .methodCount(1)
                .methodOffset(1)
                .logStrategy(new LogStrategy() {
                    @Override
                    public void log(int priority, @Nullable String tag, @NonNull String message) {
                        Log.println(priority, randomKey() + tag, message);
                    }

                    private int last;

                    private String randomKey() {
                        int random = (int) (10 * Math.random());
                        if (random == last) {
                            random = (random + 1) % 10;
                        }
                        last = random;
                        return String.valueOf(random);
                    }
                })
                .tag("----")            // tag前缀
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {

            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
