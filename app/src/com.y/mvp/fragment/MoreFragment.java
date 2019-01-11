package com.y.mvp.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.y.R;
import com.y.adapter.MoreAdapter;
import com.y.mvp.activity.ASCIIImageActivity;
import com.y.mvp.activity.Arith1Activity;
import com.y.mvp.activity.Arith2Activity;
import com.y.mvp.activity.BinderTestActivity;
import com.y.mvp.activity.NotificationTestActivity;
import com.y.mvp.activity.QuickDemo2Activity;
import com.y.mvp.activity.QuickDemoActivity;
import com.y.mvp.activity.form.FormActivity;
import com.y.mvp.activity.nested.NestedScrollActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.MorePresenter;
import com.y.route.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoreFragment extends BaseFragment<MorePresenter> {

    public static MoreFragment newInstance(){
        MoreFragment chat = new MoreFragment();
        return chat;
    }

    @BindView(R.id.rv_more)
    RecyclerView rvMore;
    private List<Integer> mores = new ArrayList<>();
    private MoreAdapter moreAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        mores.add(R.drawable.more_ascii_img);
        mores.add(R.drawable.arithmetic1);
        mores.add(R.drawable.more_adapter);
        mores.add(R.drawable.more_adapter);
        mores.add(R.drawable.more_form);
        mores.add(R.drawable.more_binder);
        mores.add(R.drawable.more_nestedscroll);
        mores.add(R.drawable.more_notification);
        mores.add(R.drawable.arithmetic1);
        mores.add(R.drawable.more_behavior);

        GridLayoutManager gManager = new GridLayoutManager(mActivity,2,GridLayoutManager.VERTICAL,false);
        rvMore.setLayoutManager(gManager);
        rvMore.setAdapter(moreAdapter = new MoreAdapter(R.layout.imageview, mores));


        initListener();
    }

    private void initListener(){
        moreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        ASCIIImageActivity.start(mActivity,view);
                        break;
                    case 1:
                        Arith1Activity.start(mActivity);
                        break;
                    case 2:
                        QuickDemoActivity.start(mActivity);
                        break;
                    case 3:
                        QuickDemo2Activity.start(mActivity);
                        break;
                    case 4:
                        FormActivity.Companion.start(mActivity);
                        break;
                    case 5:
                        BinderTestActivity.start(mActivity);
                        break;
                    case 6:
                        NestedScrollActivity.start(mActivity);
                        break;
                    case 7:
                        NotificationTestActivity.start(mActivity);
                        break;
                    case 8:
                        Arith2Activity.start(mActivity);
                        break;
                    case 9:
//                        BehaviorTestActivity.Companion.start(mActivity);
                        Router.getInstance().with(mActivity).target("store/behavior").start();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
