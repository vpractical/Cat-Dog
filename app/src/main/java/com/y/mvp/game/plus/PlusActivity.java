package com.y.mvp.game.plus;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.base.BaseActivity;
import com.y.util.T;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlusActivity extends BaseActivity {

    @BindView(R.id.tip)
    TextView tvTip;
    @BindView(R.id.rv)
    RecyclerView rv1;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_type1)
    Button btnType1;
    @BindView(R.id.btn_type2)
    Button btnType2;

    private List<Plus> plus1List = new ArrayList<>();
    private PlusAdapter plus1Adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_plus;
    }

    @Override
    protected void initInject() {

    }

    protected void init() {
        plus1List.clear();
        plus1List.addAll(Plus.init());
        plus1Adapter = new PlusAdapter(R.layout.item_plus_result, plus1List);
        LinearLayoutManager llM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(llM);
        rv1.setAdapter(plus1Adapter);

        initListener();
    }

    private void initListener() {
        plus1Adapter.setOnPlusChangedListener(new PlusAdapter.OnPlusChangedListener() {
            @Override
            public void onChanged(List<Plus> selectedList) {
                if (selectedList.size() >= 17) {
                    T.show("强化券过期了");
                    reset();
                    return;
                }
                calculate(selectedList);
            }
        });
    }

    private void calculate(List<Plus> selectedList) {
        StringBuilder sb = new StringBuilder();
        if (!selectedList.isEmpty()) {
            float failed = 1;
            sb.append("依次使用：\n\n");
            for (Plus p : selectedList) {
                sb.append("        - ").append(p.name).append(" : ").append(p.count).append("次\n");
                failed *= Math.pow(1 - p.ratio, p.count);
            }

            float succ = 1 - failed;
            DecimalFormat df = new DecimalFormat("0.00");

            sb.append("\n成功率 ：" + df.format(succ * 100) + "%");
        }
        tvTip.setText(sb.toString());
    }

    private void type1() {
        reset();
    }

    private void reset() {
        plus1Adapter.clear();
    }

    @OnClick({R.id.btn_back, R.id.btn_type1, R.id.btn_type2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_type1:
                type1();
                break;
            case R.id.btn_type2:
                type1();
                break;
        }
    }
}
