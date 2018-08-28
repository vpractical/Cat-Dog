package com.y.mvp.activity.game.astar;

import java.util.Comparator;

public class OpenComparator implements Comparator<Grid> {
    @Override
    public int compare(Grid o1, Grid o2) {
        if(o1.f > o2.f){
            return 1;
        }else if(o1.f < o2.f){
            return -1;
        }else{
            return 0;
        }
    }
}
