package com.y.mvp.base;

import com.y.component.DaggerUtilComponent;
import com.y.component.UtilComponent;
import com.y.mvp.app.App;
import com.y.util.AppUtil;

import javax.inject.Inject;


public class BaseUtil<P extends BasePresenter> implements BaseView {

    @Inject
    protected P mPresenter;

    public BaseUtil() {
        initInject();
        attachView();
    }

    protected UtilComponent getUtilComponent() {
        return DaggerUtilComponent.builder()
                .appComponent(((App) AppUtil.context().getApplicationContext()).getAppComponent())
                .build();
    }

    protected void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void initInject() {

    }
}
