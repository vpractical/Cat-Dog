package com.y.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.y.component.DaggerFragmentComponent;
import com.y.component.FragmentComponent;
import com.y.mvp.app.App;
import com.y.util.L;
import com.y.util.T;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment implements BaseView {

    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    private View rootView;
    private Unbinder unbinder;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("创建了Fragment ：" + getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean initialized = rootView != null;
        if (!initialized) {
            rootView = inflater.inflate(getLayout(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initInject();
        }
        onViewCreated();
        if (!initialized) {
            init();
        }

        ViewParent vp = rootView.getParent();
        if(vp != null){
            ((ViewGroup)vp).removeAllViews();
        }

        return rootView;
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(((App) mActivity.getApplication()).getAppComponent())
                .build();
    }

    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("销毁了Fragment ：" + getClass().getSimpleName());
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    protected abstract int getLayout();

    protected abstract void initInject();

    protected abstract void init();

    @Override
    public void showError(String msg) {
        T.show(msg);
    }
}
