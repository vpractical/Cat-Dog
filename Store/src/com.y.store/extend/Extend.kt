package com.y.store.extend

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.y.imageloader.ImageLoader
import com.y.route.Router

/**
 * ImageView使用Glide加载网络图片的扩展函数
 */
fun ImageView.urlImg(activity: Activity, url: String?){
    ImageLoader.with(activity).url(url).into(this)
}

fun Context.startRoute(context: Context,path:String){
    Router.getInstance().with(context).target(path).start()
}
