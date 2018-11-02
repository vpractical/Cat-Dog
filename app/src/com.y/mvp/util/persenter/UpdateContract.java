package com.y.mvp.util.persenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public interface UpdateContract {

    interface View extends BaseView{
        void checkFailed(String msg);
        void checkSuccess(String res);
    }

    interface Presenter extends BasePresenter<View>{
        void checkUpdate();
    }

}
