package com.y.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class Cell implements MultiItemEntity {

    public static final int MULTI_CELL = 0;
    public static final int MULTI_TOP = 1;
    public static final int MULTI_LEFT = 2;

    public static final int SPAN_COUNT = 31;
    public static final int CELL_COUNT = 3000;

    public Cell(String text,int type){
        this.text =text;
        this.type = type;
    }
    public Cell(){}

    public String text;
    public int type;

    public static List<Cell> init(){
        List<Cell> list = new ArrayList<>();

        int c = SPAN_COUNT - 1;
        int size = CELL_COUNT + c + 1 + CELL_COUNT / c + (CELL_COUNT % c > 0 ? 1 : 0);
        int index = 0;
        for (int i = 0; i < size ; i++) {
            Cell cell = new Cell();
            if(i == 0){
                cell.text = "\\";
            }else if(i < SPAN_COUNT){
                cell.text = "col " + (i - 1);
                cell.type = MULTI_TOP;
            }else if(i % SPAN_COUNT == 0){
                cell.text = "row";
                cell.type = MULTI_LEFT;
            }else{
                cell.text = "~ " + index++;
                cell.type = MULTI_CELL;
            }
            list.add(cell);
        }
        return list;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
