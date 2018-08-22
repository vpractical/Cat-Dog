package com.y.mvp.activity.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public class MainContract {

    interface View extends BaseView{
    }

    interface Presenter extends BasePresenter<View>{
    }

}
