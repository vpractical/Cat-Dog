package com.y.mvp.activity.game.astar;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculate {

    private static final String TAG = "Calculate";

    private Grid[][] grids;
    /**
     * 最后格子的xy
     */
    private int maxX, maxY;
    private Grid gridStart, gridEnd, gridCur;
    private List<Grid> openList = new ArrayList<>();
    private List<Grid> closeList = new ArrayList<>();
    private List<Grid> path = new ArrayList<>();

    public void begin(Grid[][] grids, Grid start, Grid end) {
        Log.e(TAG, "开始计算");
        this.grids = grids;
        this.gridStart = start;
        this.gridEnd = end;
        this.gridCur = start;
        openList.clear();
        closeList.clear();
        path.clear();
        openList.add(gridStart);
        echo();
    }

    private void echo() {
        if (openList.isEmpty()) return;
        sortOpen();
        closeList.add(gridCur = openList.remove(0));
        searchNear();
        resetFGH();
        if (openList.contains(gridEnd)) {
            finish();
            Log.e(TAG, "arrived");
        } else {
            echo();
        }
    }

    private void finish() {
        Grid grid = gridEnd;
        while ((grid = parent(grid)) != null) {
            path.add(grid);
        }
        Collections.reverse(path);
        path.add(gridEnd);
        onRun();
    }

    private Grid parent(Grid grid) {
        return grid.parent;
    }

    /**
     * 查找周围的格子
     */
    private void searchNear() {
        int i = gridCur.x;
        int j = gridCur.y;
        maxX = grids.length - 1;
        maxY = grids[0].length - 1;

        if (i > 0) {
            add2Open(grids[i - 1][j], true);
        }
        if (i < maxX) {
            add2Open(grids[i + 1][j], true);
        }
        if (j > 0) {
            add2Open(grids[i][j - 1], true);
        }
        if (j < maxY) {
            add2Open(grids[i][j + 1], true);
        }
        if (i > 0 && j > 0) {
            add2Open(grids[i - 1][j - 1], false);
        }
        if (i > 0 && j < maxY) {
            add2Open(grids[i - 1][j + 1], false);
        }
        if (i < maxX && j > 0) {
            add2Open(grids[i + 1][j - 1], false);
        }
        if (i < maxX && j < maxY) {
            add2Open(grids[i + 1][j + 1], false);
        }
    }

    /**
     * 格子可以到达，并且没有在closeList中,否则加入closeList
     */
    private void add2Open(Grid grid, boolean rc) {
        if (!closeList.contains(grid)) {
            if (openList.contains(grid)) {

            } else {
                if (grid.arrive) {
                    grid.parent = gridCur;
                    openList.add(grid);
                } else {
                    closeList.add(grid);
                }
            }
        }
    }

    /**
     * 重新计算fgh
     */
    private void resetFGH() {
        int size1 = grids.length;
        for (int i = 0; i < size1; i++) {
            int size2 = grids[i].length;
            for (int j = 0; j < size2; j++) {
                Grid grid = grids[i][j];
                if (!grid.arrive) continue;
                grid.g = fgh(grid, gridCur);
                grid.h = fgh(grid, gridEnd);
                grid.f = grid.g + grid.h;
            }
        }
    }

    private int fgh(Grid g1, Grid g2) {
        int sx = Math.abs(g1.x - g2.x);
        int sy = Math.abs(g1.y - g2.y);
        int sMix = Math.abs(sx - sy);
        int sLine = Math.max(sx, sy) - sMix - 1;
        if (sMix + sLine <= 0) {
            return 0;
        } else {
            return sMix * 10 + sLine * 14;
        }
    }

    /**
     * 对openList排序
     */
    private void sortOpen() {
        Collections.sort(openList, new OpenComparator());
    }

    /**
     * 行走监听
     */
    private void onRun() {
        if (calculateListener != null) {
            calculateListener.onRun(path);
        }
    }

    public interface OnCalculateListener {
        void onRun(List<Grid> path);
    }

    private OnCalculateListener calculateListener;

    public void setOnCalculateListener(OnCalculateListener l) {
        this.calculateListener = l;
    }
}
