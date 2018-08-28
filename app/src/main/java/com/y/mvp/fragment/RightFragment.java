package com.y.mvp.fragment;

import android.view.View;
import android.widget.Button;

import com.y.R;
import com.y.bean.Login;
import com.y.bean.User;
import com.y.mvp.activity.LoginActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.widget.AppToolbar;
import com.y.util.AppUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RightFragment extends BaseFragment {

    @BindView(R.id.toolbar_main)
    AppToolbar mToolbar;
    @BindView(R.id.btn_right_exit)
    Button btnExit;

    @Override
    protected int getLayout() {
        return R.layout.fragment_right;
    }

    @Override
    protected void initInject() {
        
    }

    @Override
    protected void init() {
        mToolbar.getIconView().setVisibility(View.GONE);
        mToolbar.getTitleView().setText("设置");
    }

    @OnClick(R.id.btn_right_exit)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_right_exit:
                loginOut();
                break;
        }
    }

    private void loginOut(){
        Login.exit();
        User.exit();
        AppUtil.finishAll();
        AppUtil.start(LoginActivity.class);
    }
}
