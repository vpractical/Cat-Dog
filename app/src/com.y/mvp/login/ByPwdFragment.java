package com.y.mvp.login;

import com.y.R;

public class ByPwdFragment extends BaseLoginFragment {

    public static ByPwdFragment newInstance(){
        ByPwdFragment byPwd = new ByPwdFragment();
        return byPwd;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_login_pwd;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {

        initListener();
    }

    private void initListener(){
    }
}
