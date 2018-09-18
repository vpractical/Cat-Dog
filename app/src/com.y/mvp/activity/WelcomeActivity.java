package com.y.mvp.activity;

import com.y.R;
import com.y.bean.Login;
import com.y.config.Key;
import com.y.mvp.login.LoginActivity;
import com.y.mvp.base.BaseActivity;
import com.y.util.SPUtil;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {
        Login login = (Login) SPUtil.getSingleObject(Key.LOGIN_KEY, Login.class);
        if(login != null && login.validTime > System.currentTimeMillis()){
            MainActivity.start(mActivity);
        }else{
            LoginActivity.start(mActivity);
        }
        finish();
    }
}
