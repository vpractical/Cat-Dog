package com.y.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "error_";
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler instance;
    // 程序的Context对象
    private Context mContext;
    private String versionName;
    private String versionCode;
    private String errorLog = "";

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            ex.printStackTrace();
        }
        SystemClock.sleep(2000);

        Intent intent = AppUtil.context().getPackageManager().getLaunchIntentForPackage(AppUtil.packageName());
        @SuppressLint("WrongConstant")
        PendingIntent restartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        // 退出程序
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (mgr != null) {
            // 1秒钟后重启应用
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        try {
            if (ex == null) {
                return false;
            }
            //收集异常所发生的软件系统版本名称/版本号
            collectDeviceInfo(mContext);
            //获取错误异常详细原因
            errorLog = getErrorCause(ex);
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    //  用这种方式弹不出toast  LpToastUtils.show("很抱歉,程序出现异常,即将退出");
                    Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            L.e(TAG, "--------程序异常信息明细：   " + errorLog);
            //将错误日志保存一份到SD卡
            if (BuildConfig.DEBUG) {
                saveErrorLogToSDCard(errorLog, mContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 把错误日志保存一份到SD卡
     *
     * @param errorLog
     * @param context
     */
    private void saveErrorLogToSDCard(String errorLog, Context context) {
        File saveFile = null;
        try {
            saveFile = new File(context.getFilesDir(), TimeUtil.getNowString() + "log.txt");
            FileOutputStream outputStream = new FileOutputStream(saveFile, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(errorLog);
            writer.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (saveFile != null)
                saveFile.delete();
        }

    }

    /**
     * 收集设备信息
     *
     * @param ctx
     */
    private void collectDeviceInfo(Context ctx) {
        versionName = AppUtil.versionName();
        versionCode = AppUtil.versionCode() + "";

    }


    /**
     * 获取错误原因
     *
     * @param ex
     * @return errorLog
     */
    private String getErrorCause(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String username = "";
        String errorLog = "用户名：" + username + "时间：" + TimeUtil.getNowString()
                + " ，版本号：" + versionCode + "，版本名称：" + versionName + ",异常明细：" + ex.getMessage() + "\r\n";
        errorLog += writer.toString();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorLog;
    }

}
