package com.y.mvp.fragment.presenter;

import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

public class ToolContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }

}
