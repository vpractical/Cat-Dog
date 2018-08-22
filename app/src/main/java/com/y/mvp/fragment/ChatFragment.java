package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.ChatPresenter;

public class ChatFragment extends BaseFragment<ChatPresenter> {

    public static ChatFragment newInstance(){
        ChatFragment chat = new ChatFragment();
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
