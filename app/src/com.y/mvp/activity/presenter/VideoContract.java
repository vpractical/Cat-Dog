package com.y.mvp.activity.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public interface VideoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }


}
