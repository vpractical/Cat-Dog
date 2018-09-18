package com.y.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.imageloader.ImageLoader;

import java.util.List;

public class MoreAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {
    public MoreAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
        setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if(position == 0){
                    return 2;
                }
                return 1;
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        ImageView iv = helper.getView(R.id.iv);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.with(mContext).url(item).into(iv);
    }

}
