package com.y.listener;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 引用 com.nineoldandroids:library:2.4.0
 */
public class OnDrawerListener implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerRoot;

    public OnDrawerListener(DrawerLayout view){
        this.drawerRoot = view;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        View mContent = drawerRoot.getChildAt(0);
        View mMenu = drawerView;
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;

        if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
            ViewHelper.setTranslationX(mContent,
                    mMenu.getMeasuredWidth() * (1 - scale));
            ViewHelper.setPivotX(mContent, 0);
            ViewHelper.setPivotY(mContent,
                    mContent.getMeasuredHeight() / 2);
            mContent.invalidate();
            ViewHelper.setScaleX(mContent, rightScale);
            ViewHelper.setScaleY(mContent, rightScale);
        } else {
//            ViewHelper.setTranslationX(mContent,
//                    -mMenu.getMeasuredWidth() * slideOffset);
//            ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
//            ViewHelper.setPivotY(mContent,mContent.getMeasuredHeight() / 2);
//            mContent.invalidate();
//            ViewHelper.setScaleX(mContent, rightScale);
//            ViewHelper.setScaleY(mContent, rightScale);
        }
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        drawerRoot.setDrawerLockMode(
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
