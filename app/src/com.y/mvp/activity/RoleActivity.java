package com.y.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.y.R;
import com.y.adapter.RoleAdapter;
import com.y.bean.Role;
import com.y.mvp.activity.presenter.RoleContract;
import com.y.mvp.activity.presenter.RolePresenter;
import com.y.mvp.base.BaseActivity;
import com.y.util.L;
import com.y.util.T;
import com.y.widget.PageSwitchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class RoleActivity extends BaseActivity<RolePresenter> implements RoleContract.View {

    private static final String TAG = "RoleActivity";

    @BindView(R.id.vp_role_all)
    ViewPager vpRole;
    @BindView(R.id.container_switch)
    PageSwitchView switchView;

    private RoleAdapter roleAdapter;
    private List<Role> roleList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, RoleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_role;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        initToolbar();
        roleAdapter = new RoleAdapter(mActivity, roleList);
        vpRole.setAdapter(roleAdapter);
        mPresenter.readFromServer();
        initListener();
    }

    private void initToolbar() {
        mToolBar.getTitleView().setText("手绘");
    }

    private void initListener(){
        switchView.setOnErrorReloadListener(new PageSwitchView.OnErrorReloadListener() {
            @Override
            public void onReloadClick() {
                mPresenter.readFromServer();
            }
        });
    }

    @Override
    public void emptyCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder
                .setIcon(R.mipmap.logo_round)
                .setTitle("立绘")
                .setMessage("需要先下载立绘zip,确认连着wifi,嚎请无视.")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.download();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void downLoading(int progress, String size) {
        L.e(TAG, progress + "");
        T.show(progress + "/100");
    }

    @Override
    public void show(List<Role> list) {
        roleList.clear();
        roleList.addAll(list);

        if(roleList.isEmpty()){
            switchView.showEmptyView();
        }else{
            switchView.showDataView(vpRole);
        }

        roleAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(String msg) {
        T.show(msg);
        switchView.showErrorView();
    }
}
