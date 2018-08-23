package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.MorePresenter;

public class MoreFragment extends BaseFragment<MorePresenter> {

    public static MoreFragment newInstance(){
        MoreFragment chat = new MoreFragment();
        return chat;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {

    }
}
