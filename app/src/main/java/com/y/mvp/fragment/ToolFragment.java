package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.ToolPresenter;

public class ToolFragment extends BaseFragment<ToolPresenter> {

    public static ToolFragment newInstance(){
        ToolFragment chat = new ToolFragment();
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
