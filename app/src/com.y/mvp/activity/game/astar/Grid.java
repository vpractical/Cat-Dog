package com.y.mvp.activity.game.astar;

public class Grid {
    public Grid parent;
    public int x,y;
    public int f,g,h;
    //是否可以到达
    public boolean arrive;
    //到达成本
    public float ratio;
}
