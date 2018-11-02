package com.y.mvp.activity.game.astar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.y.R;

import java.util.List;

public class AStarActivity extends AppCompatActivity implements View.OnClickListener {

    private Map map;
    private MapOpt mapOpt;
    //保存格子的二维数组
    private Grid[][] grids;
    private Calculate calculate;

    //开始 结束格子
    private Grid gridStart, gridEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astar);
        map = findViewById(R.id.map);
        mapOpt = map;
        map.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_wall).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        calculate = new Calculate();
        calculate.setOnCalculateListener(new Calculate.OnCalculateListener() {
            @Override
            public void onRun(List<Grid> path) {
                mapOpt.drawPath(path);
            }
        });
        map.setOnCheckedEndListener(new Map.OnCheckedEndListener() {
            @Override
            public void onCheckEnd(int x, int y) {
                gridEnd = grids[x][y];
                mapOpt.drawEnd(gridEnd);
                begin();
            }

            @Override
            public void onDrawWall(int x, int y) {
                Grid grid = grids[x][y];
                grid.arrive = false;
                mapOpt.drawWall(grid);
            }

            @Override
            public void onDrawFinished() {
                begin();
            }
        });

    }

    private void init() {
        int length = 60;
        int width = map.getMeasuredWidth();
        int height = map.getMeasuredHeight();

        grids = new Grid[height / length][width / length];
        int heightD = length;
        while (heightD <= height) {
            int widthD = length;
            while (widthD <= width) {
                Grid grid = new Grid();
                grid.x = heightD / length -1;
                grid.y = widthD / length - 1;
                grid.arrive = true;
                grids[heightD / length - 1][widthD / length - 1] = grid;
                widthD += length;
            }
            heightD += length;
        }


        gridStart = grids[2][3];
        gridEnd = grids[grids.length - 5][grids[0].length - 2];

        //画格子 起点 终点
        mapOpt.drawGrid(length);
        mapOpt.drawStart(gridStart);
        mapOpt.drawEnd(gridEnd);
    }

    private void begin() {
        map.clearRuns();
        calculate.begin(grids, gridStart, gridEnd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_wall:
                mapOpt.drawWall(null);
                show("绘制墙壁,不可通过");
                break;
            case R.id.btn_clear:
                map.clearWall();
                begin();
                break;
        }
    }

    private void show(String text){
        Toast t = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,30);
        t.show();
    }

}
