package com.y.mvp.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.y.component.DaggerDialogComponent;
import com.y.component.DialogComponent;
import com.y.mvp.app.App;
import com.y.util.AppUtil;

import javax.inject.Inject;

public abstract class BaseDialog<P extends BasePresenter> extends Dialog implements BaseView {

    @Inject
    P mPresenter;

    public BaseDialog(@NonNull Context context) {
        this(context,-1);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initInject();
        attachView();
    }

    protected DialogComponent getDialogComponent() {
        return DaggerDialogComponent.builder()
                .appComponent(((App) AppUtil.context().getApplicationContext()).getAppComponent())
                .build();
    }

    protected abstract void initInject();

    private void attachView(){
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void detachView(){
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        detachView();
    }

}
