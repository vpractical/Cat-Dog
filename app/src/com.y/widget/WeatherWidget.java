package com.y.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.y.R;
import com.y.mvp.activity.MainActivity;
import com.y.util.L;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        L.e("WeatherWidget : onUpdate");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, WeatherService.class));
//        } else {
//            context.startService(new Intent(context, WeatherService.class));
//        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
        views.setOnClickPendingIntent(R.id.container_widget_1,getPending(context,0));
        views.setOnClickPendingIntent(R.id.container_widget_2,getPending(context,1));
        views.setOnClickPendingIntent(R.id.container_widget_3,getPending(context,2));
        views.setOnClickPendingIntent(R.id.container_widget_4,getPending(context,3));
        views.setOnClickPendingIntent(R.id.container_widget_5,getPending(context,4));
        AppWidgetManager am = AppWidgetManager.getInstance(context);
        ComponentName widgetId = new ComponentName(context, WeatherWidget.class);
        am.updateAppWidget(widgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        //当Widget第一次创建的时候，该方法调用，然后启动后台的服务
        L.e("WeatherWidget : onEnabled");

    }

    public PendingIntent getPending(Context context,int index){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("index",index);
        return PendingIntent.getActivity(context,index,intent,0);
    }

    @Override
    public void onDisabled(Context context) {
        //当把桌面上的Widget全部都删掉的时候，调用该方法
        L.e("WeatherWidget : onDisabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        //删掉一个
        super.onDeleted(context, appWidgetIds);
    }

}

