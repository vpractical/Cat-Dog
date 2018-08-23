package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.VideoPresenter;

public class VideoFragment extends BaseFragment<VideoPresenter> {

    public static VideoFragment newInstance(){
        VideoFragment chat = new VideoFragment();
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
