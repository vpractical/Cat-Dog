package com.y.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.y.route.Route;
import com.y.route.Router;

@Route(path = "book/main")
public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toApp(View v){
        Pair<String,String> pair = Router.getInstance().get("app/ascii");
        if(pair == null) return;
        Intent intent = new Intent();
        intent.setClassName(pair.first,pair.second);
        startActivity(intent);
    }

    public void toStore(View v){
        Router.getInstance()
                .with(this)
                .target("store/main")
                .param("index",1)
                .start();
    }

}
