package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.y.R;
import com.y.mvp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Arith2Activity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, Arith2Activity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.out)
    TextView tvOut;
    @BindView(R.id.et)
    EditText etIn;
    @BindView(R.id.btn)
    Button btnSubmit;
    @BindView(R.id.btn_valid)
    Button btnValid;

    List<Integer> src = new ArrayList<>();
    List<Integer> dst = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_arithmetic;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {
        initListener();
    }

    private void initListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int n = Integer.parseInt(etIn.getText().toString());
                    computeReverse(n);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid();
            }
        });
    }

    private void computeReverse(int n) {
        src.clear();
        dst.clear();

        for (int i = 1; i <= n; i++) {
            dst.add(i);
        }

        for (int i = n * 2 - 1; i > 0; i--) {
            if (i % 2 == 0) {
                int t = src.remove(src.size() - 1);
                src.add(0, t);
            } else {
                int t = dst.get(i / 2);
                src.add(0, t);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Integer t : src) {
            sb.append(t).append(", ");
        }

        tvOut.setText(sb.toString());
    }

    private void valid() {

        boolean b = true;
        dst.clear();

        while (src.size() > 0) {
            if (b) {
                dst.add(src.remove(0));
            } else {
                int t = src.remove(0);
                src.add(src.size(), t);
            }
            b = !b;
        }

        StringBuilder sb = new StringBuilder();
        for (Integer t : dst) {
            sb.append(t).append(", ");
        }

        tvOut.setText(sb.toString());
    }
}
