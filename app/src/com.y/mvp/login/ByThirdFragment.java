package com.y.mvp.login;

import android.view.View;
import android.widget.ImageView;

import com.y.R;
import com.y.util.UMLoginUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ByThirdFragment extends BaseLoginFragment {


    @BindView(R.id.iv_login_sina)
    ImageView ivSina;
    @BindView(R.id.iv_login_wx)
    ImageView ivWX;
    @BindView(R.id.iv_login_qq)
    ImageView ivQQ;

    public static ByThirdFragment newInstance() {
        ByThirdFragment byThird = new ByThirdFragment();
        return byThird;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login_third;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.iv_login_sina, R.id.iv_login_wx, R.id.iv_login_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_sina:
                mPresenter.loginByThird(new UMLoginUtil(mActivity).sina());
                break;
            case R.id.iv_login_wx:
                mPresenter.loginByThird(new UMLoginUtil(mActivity).wx());
                break;
            case R.id.iv_login_qq:
                mPresenter.loginByThird(new UMLoginUtil(mActivity).qq());
                break;
        }
    }

}
