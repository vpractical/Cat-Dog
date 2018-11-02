package com.y.mvp.activity.presenter;

import com.y.bean.Role;
import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

import java.util.List;

public interface RoleContract {


    interface View extends BaseView {
        void emptyCache();
        void downLoading(int progress,String size);
        void show(List<Role> list);
    }

    interface Presenter extends BasePresenter<View> {
        void checkCache();
        void download();
        void zipParse();
        void save();
        void read();

    }


}
