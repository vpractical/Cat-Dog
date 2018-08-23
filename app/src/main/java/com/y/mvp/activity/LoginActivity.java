package com.y.mvp.activity;


import com.y.R;
import com.y.bean.Login;
import com.y.mvp.activity.presenter.LoginContract;
import com.y.mvp.activity.presenter.LoginPresenter;
import com.y.mvp.base.BaseActivity;
import com.y.util.T;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void showError(String msg) {
        T.show(msg);
    }

    @Override
    protected void init() {
    }

    @Override
    public void LoginSuccess(Login login) {

    }
}
