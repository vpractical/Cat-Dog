package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.y.R;
import com.y.adapter.QuickDemo2Adapter;
import com.y.bean.Quick;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.view.CatLinearlayoutManager;
import com.y.util.L;
import com.y.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuickDemo2Activity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, QuickDemo2Activity.class);
        context.startActivity(intent);
    }

    public static final String TAG = "QuickDemo2Activity";

    @BindView(R.id.swipe_quick_demo)
    SwipeRefreshLayout swipeView;
    @BindView(R.id.rv_quick_demo)
    RecyclerView rvDemo;

    private List<Quick> mData = new ArrayList<>();
    private QuickDemo2Adapter demoAdapter;
    private int currentPage;

    @Override
    protected int getLayout() {
        return R.layout.activity_quick_demo;
    }

    @Override
    protected void initInject() {
    }

    @Override
    protected void init() {
        swipeView.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        LinearLayoutManager llManager = new CatLinearlayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvDemo.setLayoutManager(llManager);
        demoAdapter = new QuickDemo2Adapter(mData);
        demoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        demoAdapter.isFirstOnly(false);
        rvDemo.setAdapter(demoAdapter);
        //没必要不用重绘rv了
        rvDemo.setHasFixedSize(true);

        //这是下拉刷新，禁用下拉加载
        swipeView.setEnabled(true);
        demoAdapter.setUpFetchEnable(false);
        demoAdapter.setEnableLoadMore(true);
        demoAdapter.setStartUpFetchPosition(1);

        refresh();
        initToolbar();
        initListener();

        //下拉加载和下拉刷新不会同时存在，所以，可以用下拉刷新的控件做下拉加载的功能
    }

    private void initToolbar() {
        mToolBar.getTitleView().setText("Adapter");
        final TextView rightTv = mToolBar.getRightText();
        rightTv.setText("...");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void refresh() {
        L.e(TAG, "refresh");
        //方式刷新的时候还可以加载更多
        demoAdapter.setEnableLoadMore(false);
        swipeView.setRefreshing(true);
        load(currentPage = 1);
    }

    private void loadMore() {
        L.e(TAG, "loadMore : " + currentPage);
        load(currentPage + 1);
    }

    private void load(final int page) {
        Quick.getData(page, new CommonSubscriber<List<Quick>>() {
            @Override
            public void onNext(List<Quick> list) {
                boolean isRefresh = page == 1;

                if (isRefresh) {
                    mData.clear();
                }

                    //上拉加载
                    demoAdapter.addData(list);
                    currentPage = page;
                    if (list.size() < 10) {
                        demoAdapter.loadMoreEnd(isRefresh);
                    } else {
                        demoAdapter.loadMoreComplete();
                    }

                if (mData.isEmpty()) {
                    //...showEmpty()
                } else {
                    //...showData()
                }

                demoAdapter.setEnableLoadMore(true);
                swipeView.setRefreshing(false);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                demoAdapter.setEnableLoadMore(true);
                swipeView.setRefreshing(false);

                demoAdapter.loadMoreFail();
                currentPage++;
            }
        });
    }

    private void initListener() {
        swipeView.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
                return false;
            }
        });

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        demoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvDemo);

        demoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T.show("onItemClick : " + position);
            }
        });

        demoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                T.show("onItemChildClick : " + position);
            }
        });
    }
}
