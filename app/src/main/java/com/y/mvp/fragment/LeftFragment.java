package com.y.mvp.fragment;

import android.view.View;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.widget.AppToolbar;

import butterknife.BindView;

public class LeftFragment extends BaseFragment {
    @BindView(R.id.toolbar_main)
    AppToolbar mToolbar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_left;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {
        mToolbar.getIconView().setVisibility(View.GONE);
        mToolbar.getTitleView().setText("菜单");
    }

}
