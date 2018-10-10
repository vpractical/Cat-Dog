package com.y.mvp.activity.nested;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.y.R;

public class NestedScrollActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, NestedScrollActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscroll);

        init();
        initListener();
    }

    private void init() {


    }

    private void initListener() {

    }
}
