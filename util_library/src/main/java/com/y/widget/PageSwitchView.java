package com.y.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.y.util.R;

/**
 * 数据 空白 错误 切换显示
 */
public class PageSwitchView extends LinearLayout {
    public PageSwitchView(Context context) {
        this(context, null);
    }

    public PageSwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(R.layout.commen_loading, R.layout.commen_empty, R.layout.commen_error);
    }

    public static final int SHOW_ERROR = 1;
    public static final int SHOW_EMPTY = 2;
    public static final int SHOW_LOADING = 3;
    public static final int SHOW_DATA = 4;

    private int loadingRes, emptyRes, errorRes;
    private View loadingView, emptyView, errorView;

    private ViewGroup.LayoutParams matchParentLayoutParams = new ViewGroup.LayoutParams(-1, -1);

    private int currentView;
    private Context context;

    private void removeFrontView() {
        removeAllViews();
    }

    private View getLoadingView() {
        if (this.loadingView == null)
            this.loadingView = View.inflate(context, loadingRes, null);
        return loadingView;
    }

    private View getEmptyView() {
        if (emptyView == null)
            this.emptyView = View.inflate(context, emptyRes, null);
        return emptyView;
    }

    private View getErrorView() {
        if (errorView == null) {
            this.errorView = View.inflate(context, errorRes, null);
        }
        return errorView;
    }

    private void animDataView(View view) {
        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(300);
        view.startAnimation(aa);
    }

    public void init(int loadingLayout, int emptyLayout, int errorLayout) {
        this.loadingRes = loadingLayout;
        this.emptyRes = emptyLayout;
        this.errorRes = errorLayout;
        loadingView = null;
        emptyView = null;
        errorView = null;
    }


    /**
     * 设置初始时候的View显示的状态
     *
     * @param what SHOW_ERROR , SHOW_EMPTY , SHOW_DATA , SHOW_LOADING
     **/
    public void setDefaultState(int what) {
        if (currentView == 0)
            currentView = what;
    }

    public boolean isShowDataView() {
        return currentView == SHOW_DATA;
    }

    public boolean isShowLoadingView() {
        return currentView == SHOW_LOADING;
    }

    public boolean isShowEmptyView() {
        return currentView == SHOW_EMPTY;
    }

    public boolean isShowErrorView() {
        return currentView == SHOW_ERROR;
    }

    public void showLoadingView() {

        if (isShowLoadingView()) return;

        removeFrontView();
        addView(getLoadingView(), matchParentLayoutParams);
        currentView = SHOW_LOADING;

        if (listener != null)
            listener.onLoadingShow();
    }

    public void showDataView(View data) {
        if (isShowDataView())
            return;
        removeFrontView();
        addView(data);
        currentView = SHOW_DATA;
        animDataView(data);

        if (listener != null)
            listener.onDataShow();
    }

    public void showEmptyView() {
        if (isShowEmptyView())
            return;
        removeFrontView();
        addView(getEmptyView(), matchParentLayoutParams);
        currentView = SHOW_EMPTY;

        if (listener != null)
            listener.onEmptyShow(getEmptyView());
    }

    public void showErrorView() {
        if (isShowErrorView())
            return;
        removeFrontView();
        addView(getErrorView(), matchParentLayoutParams);
        currentView = SHOW_ERROR;

        if (listener != null)
            listener.onErrorShow(getErrorView());
    }


    public void showLoadingView(ViewGroup.LayoutParams lp) {
        if (isShowLoadingView())
            return;
        removeFrontView();
        addView(getLoadingView(), lp);
        currentView = SHOW_LOADING;
        if (listener != null)
            listener.onLoadingShow();
    }

    public void showDataView(View data, ViewGroup.LayoutParams lp) {
        if (isShowDataView())
            return;
        removeFrontView();
        addView(data, lp);
        currentView = SHOW_DATA;
        animDataView(data);

        if (listener != null)
            listener.onDataShow();
    }

    public void showEmptyView(ViewGroup.LayoutParams lp) {
        if (isShowEmptyView())
            return;
        removeFrontView();
        addView(getEmptyView(), lp);
        currentView = SHOW_EMPTY;

        if (listener != null)
            listener.onEmptyShow(getEmptyView());
    }

    public void showErrorView(ViewGroup.LayoutParams lp) {
        if (isShowErrorView())
            return;
        removeFrontView();
        addView(getErrorView(), lp);
        currentView = SHOW_ERROR;

        if (listener != null)
            listener.onErrorShow(getErrorView());
    }

    /**
     * 错误页重新加载的回调
     */
    public interface OnErrorReloadListener {
        void onRefreshClick();
    }

    private OnErrorReloadListener errorReloadListener;

    public void setOnErrorViewRefreshListener(OnErrorReloadListener l) {
        this.errorReloadListener = l;
    }

    /**
     * 不同view切换回调
     */
    private OnViewSwitchListener listener;

    public void setOnViewSwtichListener(OnViewSwitchListener listener) {
        this.listener = listener;
    }

    public interface OnViewSwitchListener {
        void onEmptyShow(View emptyView);

        void onErrorShow(View errorView);

        void onDataShow();

        void onLoadingShow();
    }

}
