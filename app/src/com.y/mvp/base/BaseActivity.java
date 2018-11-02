package com.y.mvp.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.y.R;
import com.y.component.ActivityComponent;
import com.y.component.DaggerActivityComponent;
import com.y.mvp.app.App;
import com.y.mvp.view.AppToolbar;
import com.y.permissionlib.PermissionCat;
import com.y.util.KeyBoardUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{

    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    protected AppToolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtil.isShouldHideKeyboard(v, ev)) {
                KeyBoardUtil.hideSoftInput(v,mActivity);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCat.onRequestPermissionsResult(this,permissions, grantResults);
    }
}
