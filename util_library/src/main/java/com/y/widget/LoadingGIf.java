package com.y.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by ywb on 2016/9/18.
 */
public class LoadingGIf extends AppCompatImageView {
	public LoadingGIf(Context context) {
		super(context);
	}

	public LoadingGIf(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadingGIf(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		AnimationDrawable animationDrawable = (AnimationDrawable) getBackground();
		animationDrawable.start();
	}
}
