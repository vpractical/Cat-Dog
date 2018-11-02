package com.y.util;

import android.graphics.Color;

public class ColorUtil {

    public static int random(){
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return Color.argb(90,r,g,b);
    }
}
