package com.y.util;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus 有序事件在一个线程中有效
 * Created by pc on 2018/4/24.
 */

public class EventUtil {
	//注册事件
	public static void register(Object context) {
		if (!EventBus.getDefault().isRegistered(context)) {
			EventBus.getDefault().register(context);
		}
	}

	//解除
	public static void unregister(Object context) {
		if (EventBus.getDefault().isRegistered(context)) {
			EventBus.getDefault().unregister(context);
		}
	}

	//发送消息
	public static void post(Object event) {
		EventBus.getDefault().post(event);
	}

	public static void cancelEventDelivery(Object event){
		EventBus.getDefault().cancelEventDelivery(event);
	}
}
