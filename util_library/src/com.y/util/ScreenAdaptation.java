package com.y.util;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * 屏幕适配
 */

public class ScreenAdaptation {
    private Activity mContext;
    private int mWidth;

    public ScreenAdaptation(Activity context, int width) {
        mContext = context;
        mWidth = width;
    }

    /**
     * 注册
     */
    public void register() {
        adaptScreen4VerticalSlide(mContext, mWidth);
    }

    /**
     * 注销
     */
    public void unregister() {
        //设置为默认
        cancelAdaptScreen(mContext);
    }


    /**
     * Adapt the screen for vertical slide.
     *
     * @param designWidthInDp The size of design diagram's width, in dp,
     *                        e.g. the design diagram width is 720px, in XHDPI device,
     *                        the designWidthInDp = 720 / 2.
     */
    public static void adaptScreen4VerticalSlide(final Activity activity,
                                                 final int designWidthInDp) {
        adaptScreen(activity, designWidthInDp, true);
    }

    /**
     * Adapt the screen for horizontal slide.
     *
     * @param designHeightInDp The size of design diagram's height, in dp,
     *                         e.g. the design diagram height is 1080px, in XXHDPI device,
     *                         the designHeightInDp = 1080 / 3.
     */
    public static void adaptScreen4HorizontalSlide(final Activity activity,
                                                   final int designHeightInDp) {
        adaptScreen(activity, designHeightInDp, false);
    }
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics appDm = AppUtil.context().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = appDm.density;
        activityDm.scaledDensity = appDm.scaledDensity;
        activityDm.densityDpi = appDm.densityDpi;
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private static void adaptScreen(final Activity activity,
                                    final float sizeInDp,
                                    final boolean isVerticalSlide) {
        Point point = new Point();
        //获取屏幕的数值
        ((WindowManager) activity.getApplicationContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        //dp适配 getResources().getDisplayMetrics().density
        activity.getApplicationContext().getResources().getDisplayMetrics().density = point.x / sizeInDp;
        // context.getResources().getDisplayMetrics().density = point.y/height*2f;
        //sp适配 getResources().getDisplayMetrics().scaledDensity
        activity.getApplicationContext().getResources().getDisplayMetrics().scaledDensity = point.x / sizeInDp;
        //  context.getResources().getDisplayMetrics().scaledDensity = point.y/height*2f;

        //以下是今日头条适配方案
       /* final DisplayMetrics appDm = LpContextUtil.getContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);*/

    }


}
