package com.y.store

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.y.imageloader.ILBuilder
import com.y.imageloader.ILStrategy

class GlideConfig : ILStrategy {
    override fun clearMemoryCache() {

    }

    override fun clearDiskCache() {

    }

    override fun build(builder: ILBuilder) {
        var op = RequestOptions()

        if (builder.placeHolder > 0) {
            op = op.placeholder(builder.placeHolder)
        }

        if (builder.errorHolder > 0) {
            op = op.error(builder.errorHolder)
        }

        if (builder.circle) {
            op = op.circleCrop()
        }

        val rm: RequestManager
        if (builder.activity != null) {
            rm = Glide.with(builder.activity)
        } else if (builder.fragment != null) {
            rm = Glide.with(builder.fragment)
        } else if (builder.context != null) {
            rm = Glide.with(builder.context)
        } else {
            throw NullPointerException("library说明.with(?)")
        }

        var rb: RequestBuilder<*>
        if (builder.targetUrl != null) {
            rb = rm.load(builder.targetUrl)
        } else if (builder.targetResource > 0) {
            rb = rm.load(builder.targetResource)
        } else if (builder.targetBitmap != null) {
            rb = rm.load(builder.targetBitmap)
        } else if (builder.targetDrawable != null) {
            rb = rm.load(builder.targetDrawable)
        } else if (builder.targetFile != null) {
            rb = rm.load(builder.targetFile)
        } else if (builder.targetUri != null) {
            rb = rm.load(builder.targetUri)
        } else {
            throw NullPointerException("library说明.load(?)")
        }

        rb = rb.apply(op)

        if (builder.targetView != null) {
            rb.into(builder.targetView)
        } else {
            throw NullPointerException("library说明.into(?)")
        }

    }

    override fun pause(tag: String) {

    }

    override fun cancel(tag: String) {

    }
}
