package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.y.R;
import com.y.adapter.QuickDemoAdapter;
import com.y.bean.Quick;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.observer.CommonSubscriber;
import com.y.mvp.view.CatLinearlayoutManager;
import com.y.util.L;
import com.y.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuickDemoActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, QuickDemoActivity.class);
        context.startActivity(intent);
    }

    public static final String TAG = "QuickDemoActivity";

    @BindView(R.id.swipe_quick_demo)
    SwipeRefreshLayout swipeView;
    @BindView(R.id.rv_quick_demo)
    RecyclerView rvDemo;

    private List<Quick> mData = new ArrayList<>();
    private QuickDemoAdapter demoAdapter;
    private int currentPage, currentPageUp;

    /**
     * item拖动
     */
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        demoAdapter = new QuickDemoAdapter(R.layout.item_quick_demo, mData);
        demoAdapter.addHeaderView(makeHeaderView());
        demoAdapter.addFooterView(makeFooterView());
        rvDemo.setAdapter(demoAdapter);
        //没必要不用重绘rv了
        rvDemo.setHasFixedSize(true);

        //这是下拉刷新，禁用下拉加载
        swipeView.setEnabled(true);
        demoAdapter.setUpFetchEnable(false);
        demoAdapter.setEnableLoadMore(true);
        demoAdapter.setStartUpFetchPosition(1);

        //使用item拖动时，adapter需继承BaseItemDraggableAdapter
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(demoAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(rvDemo);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        demoAdapter.enableSwipeItem();
        demoAdapter.setOnItemSwipeListener(onItemSwipeListener);
        demoAdapter.enableDragItem(mItemTouchHelper);
        demoAdapter.setOnItemDragListener(onItemDragListener);
        //进制侧滑
        //demoAdapter.disableSwipeItem();

        refresh();
        initToolbar();
        initListener();

        //下拉加载和下拉刷新不会同时存在，所以，可以用下拉刷新的控件做下拉加载的功能
    }

    private void initToolbar() {
        mToolBar.getTitleView().setText("Adapter");
        final TextView rightTv = mToolBar.getRightText();
        rightTv.setText("下拉刷新");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeView.setEnabled(demoAdapter.isUpFetchEnable());
                demoAdapter.setUpFetchEnable(!swipeView.isEnabled());
                if (demoAdapter.isUpFetchEnable()) {
                    rightTv.setText("下拉加载");
                    if(demoAdapter.getHeaderLayout() != null){
                        //有header时，下拉加载不能用
                        demoAdapter.removeAllHeaderView();
                    }
                } else {
                    rightTv.setText("下拉刷新");
                }
            }
        });
    }

    private View makeHeaderView() {
        TextView tv = new TextView(mActivity);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        tv.setGravity(Gravity.CENTER);
        int count = demoAdapter.getHeaderLayout() == null ? 1 : (demoAdapter.getHeaderLayout().getChildCount() + 1);
        tv.setText("header : " + count);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoAdapter.addHeaderView(makeHeaderView());
            }
        });
        return tv;
    }

    private View makeFooterView() {
        TextView tv = new TextView(mActivity);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        tv.setGravity(Gravity.CENTER);
        int count = demoAdapter.getFooterLayout() == null ? 1 : (demoAdapter.getFooterLayout().getChildCount() + 1);
        tv.setText("footer : " + count);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoAdapter.addFooterView(makeFooterView());
            }
        });
        return tv;
    }

    private void upFetch() {
        L.e(TAG, "upFetch : " + currentPageUp);
        demoAdapter.setUpFetching(true);
        load(currentPageUp - 1);
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

                if (page > 0) {
                    //上拉加载
                    demoAdapter.addData(list);
                    currentPage = page;
                    if (list.size() < 10) {
                        demoAdapter.loadMoreEnd(isRefresh);
                    } else {
                        demoAdapter.loadMoreComplete();
                    }
                } else {
                    //下拉加载
                    demoAdapter.addData(0, list);
                    currentPageUp = page;
                    if (list.size() < 10) {
                        demoAdapter.setUpFetchEnable(false);
                    }
                }

                if (mData.isEmpty()) {
                    //...showEmpty()
                } else {
                    //...showData()
                }

                demoAdapter.setEnableLoadMore(true);
                demoAdapter.setUpFetching(false);
                swipeView.setRefreshing(false);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                demoAdapter.setEnableLoadMore(true);
                demoAdapter.setUpFetching(false);
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

        demoAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                upFetch();
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

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            L.e(TAG, "onItemDragStart");
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            holder.setTextColor(R.id.tv_quick_demo_id, Color.RED);
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
//            L.e(TAG, "onItemDragMoving from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            L.e(TAG, "onItemDragEnd");
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            holder.setTextColor(R.id.tv_quick_demo_id, Color.GREEN);
        }
    };
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            L.e(TAG, "onItemSwipeStart: " + pos);
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            holder.setTextColor(R.id.tv_quick_demo_id, Color.RED);
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            L.e(TAG, "clearView: " + pos);
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            holder.setTextColor(R.id.tv_quick_demo_id, Color.GREEN);
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            L.e(TAG, "onItemSwiped: " + pos);
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
//            L.e(TAG, "onItemSwipeMoving: " + dX);
            mPaint.setTextSize(40);
            canvas.drawText("swipeMoving", 0, 40, mPaint);
        }
    };
}
