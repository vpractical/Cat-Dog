package com.y.mvp.game.astar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Map extends View implements MapOpt {

    public Map(Context context) {
        super(context);
        init();
    }

    public Map(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Map(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    private int length;//格子边长
    private Grid startGrid, endGrid;//起点、终点

    private List<Grid> runs = new ArrayList<>();//行走路径
    private List<Grid> walls = new ArrayList<>();//墙壁

    private boolean isDrawWall;

    private int touchX, touchY;

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //画格子
        int widthD = length;
        int heightD = length;
        while (widthD < width) {
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(1);
            canvas.drawLine(widthD, 0, widthD, height, paint);
            widthD += length;
        }
        while (heightD < height) {
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(1);
            canvas.drawLine(0, heightD, width, heightD, paint);
            heightD += length;
        }

        if (startGrid != null) {
            paint.setColor(Color.RED);
            canvas.drawRect(startGrid.y * length, startGrid.x * length, startGrid.y * length + length, startGrid.x * length + length, paint);
        }

        if (endGrid != null) {
            paint.setColor(Color.BLACK);
            canvas.drawRect(endGrid.y * length, endGrid.x * length, endGrid.y * length + length, endGrid.x * length + length, paint);
        }

        if(walls.size() > 0){
            paint.setColor(Color.DKGRAY);
            int size = walls.size();
            for (int i = 1; i < size; i++) {
                Grid g1 = walls.get(i);
                canvas.drawRect(g1.y * length,g1.x * length,g1.y * length + length,g1.x * length + length,paint);
            }
        }

        if (runs.size() >= 2) {
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(5);
            int size = runs.size();
            for (int i = 1; i < size; i++) {
                Grid g1 = runs.get(i - 1);
                Grid g2 = runs.get(i);
                canvas.drawLine(g1.y * length + length / 2, g1.x * length + length / 2, g2.y * length + length / 2, g2.x * length + length / 2, paint);
            }
        }

    }

    public void clearRuns() {
        runs.clear();
    }

    public void clearWall(){
        for (Grid grid:walls) {
            grid.arrive = true;
        }
        walls.clear();
    }

    @Override
    public void drawGrid(int w) {
        this.length = w;
        invalidate();
    }

    @Override
    public void drawLine(Grid g1, Grid g2) {

    }

    @Override
    public void drawPath(List<Grid> path) {
        runs.clear();
        runs.addAll(path);
        invalidate();
    }

    @Override
    public void drawWall(Grid grid) {
        if(grid == null){
            isDrawWall = true;
            return;
        }
        walls.add(grid);
        invalidate();
    }

    @Override
    public void drawStart(Grid start) {
        this.startGrid = start;
        invalidate();
    }

    @Override
    public void drawEnd(Grid end) {
        this.endGrid = end;
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (checkedEndListener == null) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = (int) event.getY() / length;
                touchY = (int) event.getX() / length;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getY() / length;
                int y = (int) event.getX() / length;
                if(x != touchX || y != touchY){
                    if (isDrawWall) {
                        checkedEndListener.onDrawWall(x,y);
                    }
                }
                touchX = x;
                touchY = y;
                break;
            case MotionEvent.ACTION_UP:
                touchX = (int) event.getY() / length;
                touchY = (int) event.getX() / length;
                if (isDrawWall) {
                    checkedEndListener.onDrawFinished();
                } else {
                    checkedEndListener.onCheckEnd(touchX, touchY);
                }
                isDrawWall = false;
                break;
        }


        return true;
    }

    private OnCheckedEndListener checkedEndListener;

    public interface OnCheckedEndListener {
        void onCheckEnd(int x, int y);

        void onDrawWall(int x, int y);

        void onDrawFinished();//绘制完成
    }

    public void setOnCheckedEndListener(OnCheckedEndListener l) {
        this.checkedEndListener = l;
    }
}
