package com.y.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.Toast;

public class T {
    private static final Toast toast = Toast.makeText(AppUtil.context(), "", Toast.LENGTH_SHORT);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    static {
        toast.setGravity(Gravity.CENTER, 0, 0);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            layout.setBackgroundColor(R.drawable.bg_toast);
//            TextView v = toast.getView().findViewById(android.R.id.message);
//            v.setTextColor(Color.WHITE);
//            v.setTextSize(16);
    }

    /**
     * 推荐方式：吐司
     *
     * @param str
     */
    public static void show(String str) {
        show(str, Toast.LENGTH_SHORT);
    }

    public static void show(final String str, final int time) {
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
     */
    private static void showOnUiThread(@NonNull String str, int time) {
        toast.setText(str);
        toast.setDuration(time);
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
