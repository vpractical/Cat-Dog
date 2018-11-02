package com.y.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.y.route.Router;
import com.y.router_annotations.Route;

@Route(path = "book/main")
public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity_main);
    }

    public void toApp(View v){
        Class clz = Router.getInstance().get("app/ascii");
        if(clz == null) return;
        Intent intent = new Intent();
        intent.setClass(this,clz);
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
