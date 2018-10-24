package com.y.mvp.login;

import com.y.mvp.activity.MainActivity;
import com.y.mvp.activity.presenter.LoginContract;
import com.y.mvp.activity.presenter.LoginPresenter;
import com.y.mvp.base.BaseFragment;

public abstract class BaseLoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {

    @Override
    public void loginSuccess() {
        MainActivity.start(mActivity);
        mActivity.finish();
    }

}
