package com.y.mvp.game.astar;

import java.util.List;

public interface MapOpt {
    /**
     * 画格子
     */
    void drawGrid(int w);
    /**
     * 画路径
     */
    void drawLine(Grid g1, Grid g2);

    void drawPath(List<Grid> path);

    /**
     * 画墙,不可通过
     */
    void drawWall(Grid grid);

    /**
     * 画河流,通过难度1.3
     */
//    void drawRiver(Grid grid);

    /**
     * 起点终点
     */
    void drawStart(Grid start);
    void drawEnd(Grid end);
}
