package com.y.mvp.fragment.presenter;

import com.y.bean.HotStraetgyEntity;
import com.y.mvp.base.BasePresenter;
import com.y.mvp.base.BaseView;

import java.util.List;

public interface VideoContract {

    interface View extends BaseView{
        void loadSuccess(int page,List<HotStraetgyEntity.ItemListEntity> list);
        void loadFailed(int page,String msg);
    }

    interface Presenter extends BasePresenter<View>{
        void load(int page);
    }

}
