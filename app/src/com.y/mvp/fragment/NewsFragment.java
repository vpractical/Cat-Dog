package com.y.mvp.fragment;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.NewsContract;
import com.y.mvp.fragment.presenter.NewsPresenter;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View{

    public static NewsFragment newInstance(){
        NewsFragment news = new NewsFragment();
        return news;
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
