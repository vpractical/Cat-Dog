package com.y.mvp.fragment.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public class GameContract {

    interface View extends BaseView{
    }

    interface Presenter extends BasePresenter<View>{
    }

}
