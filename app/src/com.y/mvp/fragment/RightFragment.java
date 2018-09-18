package com.y.mvp.fragment;

import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.y.R;
import com.y.bean.Login;
import com.y.bean.User;
import com.y.config.Key;
import com.y.config.Config;
import com.y.mvp.login.LoginActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.view.AppToolbar;
import com.y.util.AppUtil;
import com.y.util.L;
import com.y.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.widget.WheelView;

public class RightFragment extends BaseFragment {

    @BindView(R.id.toolbar_main)
    AppToolbar mToolbar;
    @BindView(R.id.btn_right_exit)
    Button btnExit;
    @BindView(R.id.wheel_right)
    WheelView wheelView;

    private List<Pair<Integer, String>> loginTypes = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_right;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {

        initWheel();
        initToolbar();
        initListener();
    }

    private void initWheel() {
        loginTypes = Config.loginTypes();
        List<String> types = new ArrayList<>(loginTypes.size());
        int loginTypeIndex = SPUtil.getCommonInt(Key.LOGIN_TYPE, 0);
        for (int i = 0; i < loginTypes.size(); i++) {
            types.add(loginTypes.get(i).second);
        }
        wheelView.setItems(types);
        wheelView.setOffset(1);
        wheelView.setCycleDisable(false);
        wheelView.setTextColor(Color.LTGRAY, Color.DKGRAY);
        wheelView.setTextSize(14);
        wheelView.setUseWeight(true);
//        wheelView.setDividerConfig(null);
        wheelView.setTextPadding(0);
        wheelView.setSelectedIndex(loginTypeIndex);
    }

    private void initToolbar() {
        mToolbar.getIconView().setVisibility(View.GONE);
        mToolbar.getTitleView().setText("设置");
    }

    private void initListener() {
        wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                SPUtil.putCommonInt(Key.LOGIN_TYPE, loginTypes.get(index).first);
                L.e("已选择登录方式：" + loginTypes.get(index).second);
            }
        });
    }

    @OnClick(R.id.btn_right_exit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_right_exit:
                loginOut();
                break;
        }
    }

    private void loginOut() {
        Login.exit();
        User.exit();
        AppUtil.finishAll();
        AppUtil.start(LoginActivity.class);
    }
}
