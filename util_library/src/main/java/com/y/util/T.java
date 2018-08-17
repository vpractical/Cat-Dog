package com.y.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

public class T {
    private static Toast toast;
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 推荐方式：吐司
     *
     * @param str
     */
    public static void show(String str) {
        show(str, Toast.LENGTH_SHORT);
    }


    private static void show(final String str, final int time) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            // 主线程
            showOnUiThread(str, time);
        } else {
            // 子线程
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    showOnUiThread(str, time);
                }
            });
        }
    }

    /**
     * 在UI线程显示（居中）
     *
     * @param str
     * @param time
     */
    @SuppressLint("ShowToast")
    private static void showOnUiThread(String str, int time) {
        if (toast == null) {
            toast = Toast.makeText(AppUtil.context(), str, time);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
            toast.setDuration(time);
        }
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
