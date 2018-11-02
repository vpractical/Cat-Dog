package com.y.mvp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.activity.nested.NestedScrollActivity;
import com.y.util.TimeUtil;

import java.io.File;


/**
 * 8.0后从不同的NotificationChannel发起的通知，分被禁用全体通知，和被禁用某个Channel
 */
public class NotificationTestActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, NotificationTestActivity.class));
    }

    private static final String CANCEL_ACTION = "notification_cancel";

    private Activity mActivity;
    private TextView tv1, tv2;
    private NotificationManager manager;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
        registerReceiver();
    }

    private void init() {
        mActivity = this;
        tv1 = findViewById(R.id.tv_create1);
        tv1.setOnClickListener(this);
        tv2 = findViewById(R.id.tv_create2);
        tv2.setOnClickListener(this);

        tv1.setText("普通通知");
        tv2.setText("自定义通知布局");

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void create1() {
        String channelId = "1";
        String channelName = "Channel1";

        NotificationChannel channel = new NotificationChannel(channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT);//ChannelId为"1",ChannelName为"Channel1"
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
//        channel.enableVibration(true);
//        channel.setVibrationPattern(new long[]{500});
        manager.createNotificationChannel(channel);

        /*
         * 调用PendingIntent的静态放法创建一个 PendingIntent对象用于点击通知之后执行的操作，
         * PendingIntent可以理解为延时的Intent，在这里即为点击通知之后执行的Intent
         * 这里调用getActivity(Context context, int requestCode, Intent intent, int flag)方法
         * 表示这个PendingIntent对象启动的是Activity，类似的还有getService方法、getBroadcast方法
         */
        Intent intent = new Intent(this, ASCIIImageActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        final int notificationId = (int) (1000 * Math.random());
//        final Notification.Builder builder = new Notification.Builder(mActivity, channelId); //与channelId对应
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity, channelId);
        //icon title text必须包含，不然影响桌面图标小红点的展示
        builder.setSmallIcon(R.drawable.more_dark)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.more_white))
                .setContentTitle("id:" + notificationId)
                .setContentText(channelName + "_" + channelId)
                .setNumber(3) //久按桌面图标时允许的此条通知的数量
                .setShowWhen(true)
                .setWhen(System.currentTimeMillis()) // 设定通知显示的时间
                .setContentIntent(pi) // 设定点击通知之后启动的内容，这个内容由方法中的参数：PendingIntent对象决定
                .setPriority(Notification.PRIORITY_MAX) // 设置通知的优先级
                .setAutoCancel(true); // 设置点击通知之后通知是否消失
//                .setDefaults(Notification.DEFAULT_ALL)  // 设置通知提醒方式为系统默认的提醒方式
                /*
                 * 设置震动，用一个 long 的数组来表示震动状态，这里表示的是先震动1秒、静止1秒、再震动1秒，这里以毫秒为单位
                 * 如果要设置先震动1秒，然后停止0.5秒，再震动2秒则可设置数组为：long[]{1000, 500, 2000}。
                 * 别忘了在AndroidManifest配置文件中申请震动的权限
                 */
//                .setVibrate(new long[]{500})
                /*
                 * 设置手机的LED灯为蓝色并且灯亮2秒，熄灭1秒，达到灯闪烁的效果，不过这些效果在模拟器上是看不到的，
                 * 需要将程序安装在真机上才能看到对应效果，如果不想设置这些通知提示效果，
                 * 可以直接设置：setDefaults(Notification.DEFAULT_ALL);
                 * 意味将通知的提示效果设置为系统的默认提示效果
                 */
//                .setLights(Color.BLUE, 2000, 1000);

        /* max用于设定进度的最大数，progress用于设定当前的进度，indeterminate用于设定是否是一个确定进度的进度条。
         * 通过indeterminate的设置，可以实现两种不同样式的进度条，一种是有进度刻度的（true）,一种是循环流动的（false）
         */
        if (notificationId / 100 >= 4) {
            builder.setProgress(100, notificationId / 10, false);
        } else if (notificationId / 100 <= 1) {
            builder.setProgress(0, 0, true);
        }

        File soundFile = new File("/system/media/audio/ringtones/Luna.ogg");
        if (soundFile.exists()) {
            builder.setSound(Uri.fromFile(soundFile));
        }

        /*
         * 使用从系统服务获得的通知管理器发送通知，第一个参数是通知的id，不同的通知应该有不同的id，
         * 这样当我们要取消哪条通知的时候我们调用notificationManager（通知管理器）.cancel(int id)
         * 方法并传入发送通知时的对应id就可以了。在这里如果我们要取消这条通知，
         * 我们调用notificationManager.cancel(1);就可以了
         * 第二个参数是要发送的通知对象
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.notify(notificationId, builder.build());
            }
        }, 3000);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void create2() {
        String channelId = "2";
        String channelName = "Channel2";

        NotificationChannel channel = new NotificationChannel(channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        manager.createNotificationChannel(channel);

        Intent button1I = new Intent(mActivity, NestedScrollActivity.class);
        PendingIntent button1PI = PendingIntent.getActivity(mActivity, 0, button1I, 0);
        Intent button2I = new Intent(mActivity, MainActivity.class);
        button2I.putExtra("index", 3);
        PendingIntent button2PI = PendingIntent.getActivity(mActivity, 0, button2I, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent button3I = new Intent(CANCEL_ACTION);
        PendingIntent button3PI = PendingIntent.getBroadcast(this, 0, button3I, 0);
        /*
         * 通知布局如果使用自定义布局文件中的话要通过RemoteViews类来实现，
         * 其实无论是使用系统提供的布局还是自定义布局，都是通过RemoteViews类实现，如果使用系统提供的布局，
         * 系统会默认提供一个RemoteViews对象。如果使用自定义布局的话这个RemoteViews对象需要我们自己创建，
         * 并且加入我们需要的对应的控件事件处理，然后通过setContent(RemoteViews remoteViews)方法传参实现
         */
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_test);
        /*
         * 对于自定义布局文件中的控件通过RemoteViews类的对象进行事件处理
         */
        remoteViews.setOnClickPendingIntent(R.id.tv_notify1, button1PI);
        remoteViews.setOnClickPendingIntent(R.id.tv_notify2, button2PI);
        remoteViews.setOnClickPendingIntent(R.id.tv_notify3, button3PI);
        remoteViews.setTextViewText(R.id.tv_notify3, "" + TimeUtil.getNowString(TimeUtil.DATE_FORMAT_HMS));

        Intent intent = new Intent(this, ASCIIImageActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(mActivity, channelId); //与channelId对应

        builder
                .setContentTitle("自定义")
                .setContentText(channelName + "_" + channelId)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.game_white) // 创建通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_dark)) // 创建通知的大图标
                /*
                 * 是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
                 */
                .setDefaults(Notification.DEFAULT_ALL)  // 设置通知提醒方式为系统默认的提醒方式
                .setContent(remoteViews) // 通过设置RemoteViews对象来设置通知的布局，这里我们设置为自定义布局
                .setContentIntent(pi)
                .build(); // 创建通知（每个通知必须要调用这个方法来创建）

        manager.notify(0x112, builder.build()); // 发送通知
    }

    private void registerReceiver() {
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    manager.cancel(0x112);
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(CANCEL_ACTION);
            mActivity.registerReceiver(receiver, filter);
        }
    }

    private void unRegisterReceiver() {
        if (receiver != null) {
            mActivity.unregisterReceiver(receiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create1:
                create1();
                break;
            case R.id.tv_create2:
                create2();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }
}
