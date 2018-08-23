package com.y.mvp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.GamePresenter;
import com.y.mvp.game.astar.AStarActivity;
import com.y.mvp.game.party.PartyActivity;
import com.y.mvp.game.plus.PlusActivity;

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
        return R.layout.fragment_chat;
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
    }

    private TextView tv(){
        TextView tv = new TextView(mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
        tv.setLayoutParams(params);
        tv.setText("Empty");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.argb(50,color(),color(),color()));
        containerRoot.addView(tv);
        return tv;
    }

    private int color(){
        return (int) (Math.random() * 255);
    }

    private void start(Class act){
        startActivity(new Intent(mActivity,act));
    }
}
