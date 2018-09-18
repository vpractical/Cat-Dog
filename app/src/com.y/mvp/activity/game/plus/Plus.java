package com.y.mvp.activity.game.plus;

import java.util.ArrayList;
import java.util.List;

public class Plus implements Cloneable{
    public static List<Plus> init(){
        List<Plus> pluses = new ArrayList<>();
        pluses.add(new Plus("黑铁  (1%)",0.01f,"#efebbb"));
        pluses.add(new Plus("青铜  (5%)",0.05f,"#e8e0af"));
        pluses.add(new Plus("白银(10%)",0.1f,"#ded19d"));
        pluses.add(new Plus("黄金(30%)",0.3f,"#d6c08e"));
        pluses.add(new Plus("铂金(50%)",0.5f,"#caaa77"));
        pluses.add(new Plus("翡翠(70%)",0.7f,"#be9964"));
        pluses.add(new Plus("钻石(90%)",0.9f,"#ab7841"));
        return pluses;
    }

    public Plus(String name, float ratio, String c){
        this.name = name;
        this.ratio = ratio;
        this.c = c;
    }

    public String name;
    public float ratio;//成功率
    public int count = 1;
    public String c;

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Plus)obj).name);
    }

    @Override
    public Plus clone(){
        Plus p = null;
        try {
            p = (Plus) super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return p;
    }
}
