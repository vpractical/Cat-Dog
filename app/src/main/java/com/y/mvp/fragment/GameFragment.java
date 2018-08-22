package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.GamePresenter;

public class GameFragment extends BaseFragment<GamePresenter> {

    public static GameFragment newInstance(){
        GameFragment chat = new GameFragment();
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
