package com.y.mvp.game.party;

import android.view.View;
import android.widget.Button;

import com.y.R;
import com.y.mvp.base.BaseActivity;

import butterknife.BindView;


public class PartyActivity extends BaseActivity{

    @BindView(R.id.btn_back)
    Button btnBack;

    protected void init() {

        initListener();
    }

    private void initListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_party;
    }

    @Override
    protected void initInject() {

    }
}
