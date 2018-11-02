package com.y.mvp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.activity.RoleActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.GamePresenter;
import com.y.mvp.activity.game.astar.AStarActivity;
import com.y.mvp.activity.game.party.PartyActivity;
import com.y.mvp.activity.game.plus.PlusActivity;
import com.y.route.Router;
import com.y.util.ColorUtil;

import butterknife.BindView;

public class GameFragment extends BaseFragment<GamePresenter> {

    public static GameFragment newInstance(){
        GameFragment chat = new GameFragment();
        return chat;
    }

    @BindView(R.id.container_game_root)
    ViewGroup containerRoot;

    @Override
    protected int getLayout() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        //寻路
        TextView tvAStar = tv();
        tvAStar.setText("寻路");
        tvAStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AStarActivity.class);
            }
        });

        //深渊派对
        TextView tvD = tv();
        tvD.setText("深渊派对");
        tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PartyActivity.class);
            }
        });

        //强化增幅
        TextView tvPlus = tv();
        tvPlus.setText("强化增幅");
        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PlusActivity.class);
            }
        });

        //立绘
        TextView tvRole = tv();
        tvRole.setText("立绘");
        tvRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RoleActivity.class);
            }
        });

        //视图
        TextView tvView = tv();
        tvView.setText("视图");
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RoleActivity.class);
            }
        });

        //书籍
        TextView tvBook = tv();
        tvBook.setText("书籍");
        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance()
                        .with(mActivity)
                        .target("book/main")
                        .start();
            }
        });

        //收藏
        TextView tvStore = tv();
        tvStore.setText("收藏");
        tvStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance()
                        .with(mActivity)
                        .target("store/main")
                        .start();
            }
        });
    }

    private TextView tv(){
        TextView tv = new TextView(mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
        tv.setLayoutParams(params);
        tv.setText("Empty");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(ColorUtil.random());
        containerRoot.addView(tv);
        return tv;
    }

    private void start(Class act){
        startActivity(new Intent(mActivity,act));
    }
}
