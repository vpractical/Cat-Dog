package com.y.util;

import android.content.Context;

import com.cache.disklrucachehelper.DiskLruCacheHelper;

import java.io.IOException;

/**
 * 磁盘缓存
 */
public class DiskCache {
    private DiskCache() {
    }

    private static DiskLruCacheHelper cacheHelper;

    public static void init(Context context) {
        try {
            cacheHelper = new DiskLruCacheHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void put(String key, String value) {
        if (cacheHelper != null && value != null) {
            cacheHelper.put(key, value);
        }
    }

    public static String get(String key) {
        if (cacheHelper != null && cacheHelper.getAsString(key) != null) {
            return cacheHelper.getAsString(key);
        }
        return null;
    }
}
