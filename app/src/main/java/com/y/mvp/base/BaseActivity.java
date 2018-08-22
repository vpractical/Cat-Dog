package com.y.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.y.R;
import com.y.component.ActivityComponent;
import com.y.component.DaggerActivityComponent;
import com.y.mvp.app.App;
import com.y.util.T;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements BaseView,View.OnClickListener{

    @Inject
    protected P mPresenter;
    private Activity mActivity;

    @BindView(R.id.toolbar_main)
    public Toolbar mToolBar;
    @BindView(R.id.iv_toolbar_icon)
    public ImageView mIvBack;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mActivity = this;
        ButterKnife.bind(this);
        initInject();
        attach();
        init();
    }

    private void attach(){
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void detach(){
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void setTitle(String title){
       mTvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        if(v == mIvBack){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detach();
    }

    @Override
    public void showError(String msg) {
        T.show(msg);
    }

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(((App)getApplication()).getAppComponent())
                .build();
    }

    /**
     * 获取资源文件
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 注解当前activity
     */
    protected abstract void initInject();

    /**
     * 初始化数据
     */
    protected abstract void init();

}
