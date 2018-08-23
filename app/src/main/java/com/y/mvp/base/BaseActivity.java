package com.y.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.y.R;
import com.y.component.ActivityComponent;
import com.y.component.DaggerActivityComponent;
import com.y.mvp.app.App;
import com.y.mvp.widget.AppToolbar;
import com.y.util.T;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements BaseView{

    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    protected AppToolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mToolBar = findViewById(R.id.toolbar_main);
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
