package com.y.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.bean.Quick;
import com.y.imageloader.ImageLoader;

import java.util.List;

public class QuickDemoAdapter extends BaseItemDraggableAdapter<Quick,BaseViewHolder> {

    public QuickDemoAdapter(int layoutResId, @Nullable List<Quick> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Quick item) {
        ImageView iv = helper.getView(R.id.iv_quick_demo);
        ImageLoader.with(mContext).url(item.image).placeHolder(R.drawable.more_dark).into(iv);
        TextView tvId = helper.getView(R.id.tv_quick_demo_id);
        tvId.setText("id : " + item.id + "\ncontent : " + item.content + "\nmulitType: " + item.multiType);

        helper.addOnClickListener(R.id.tv_quick_demo_id);
        helper.addOnLongClickListener(R.id.tv_quick_demo_id);
    }
}
