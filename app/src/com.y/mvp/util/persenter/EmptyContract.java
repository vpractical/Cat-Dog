package com.y.mvp.util.persenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public interface EmptyContract {

    interface View extends BaseView{
    }

    interface Presenter extends BasePresenter<View>{
    }

}
