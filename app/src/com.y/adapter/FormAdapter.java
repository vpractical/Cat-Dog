package com.y.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.bean.Cell;
import com.y.bean.Quick;
import com.y.util.ColorUtil;

import java.util.List;

public class FormAdapter extends BaseMultiItemQuickAdapter<Cell, BaseViewHolder> {


    public FormAdapter(List<Cell> data) {
        super(data);
        addItemType(Quick.MULTI_TEXT, R.layout.item_form_item);
        addItemType(Quick.MULTI_IMAGE, R.layout.item_form_item);
        addItemType(Quick.MULTI_TEXT_IMAGE, R.layout.item_form_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Cell item) {
        TextView tv = helper.getView(R.id.tv);
        tv.setText(item.text);
        switch (helper.getItemViewType()) {
            case Cell.MULTI_CELL:
//                tv.setBackgroundColor(ColorUtil.random());
                break;
            case Cell.MULTI_TOP:
                tv.setBackgroundColor(ColorUtil.random());
                break;
            case Cell.MULTI_LEFT:
                tv.setBackgroundColor(ColorUtil.random());
                break;
        }
    }
}
