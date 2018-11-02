package com.y.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.bean.Quick;
import com.y.imageloader.ImageLoader;

import java.util.List;

public class QuickDemo2Adapter extends BaseMultiItemQuickAdapter<Quick, BaseViewHolder> {

    public QuickDemo2Adapter(List<Quick> data) {
        super(data);
        addItemType(Quick.MULTI_TEXT,R.layout.item_quick_demo);
        addItemType(Quick.MULTI_IMAGE,R.layout.item_quick_demo);
        addItemType(Quick.MULTI_TEXT_IMAGE,R.layout.item_quick_demo);
    }

    @Override
    protected void convert(BaseViewHolder helper, Quick item) {

        switch (helper.getItemViewType()) {
            case Quick.MULTI_TEXT:
                TextView tvId = helper.getView(R.id.tv_quick_demo_id);
                tvId.setText("id : " + item.id + "\ncontent : " + item.content + "\nmulitType: " + item.multiType);
                break;
            case Quick.MULTI_IMAGE:
                ImageView iv = helper.getView(R.id.iv_quick_demo);
                ImageLoader.with(mContext).url(item.image).placeHolder(R.drawable.more_dark).into(iv);
                break;
            case Quick.MULTI_TEXT_IMAGE:
                ImageView iv2 = helper.getView(R.id.iv_quick_demo);
                ImageLoader.with(mContext).url(item.image).placeHolder(R.drawable.more_dark).into(iv2);
                TextView tvId2 = helper.getView(R.id.tv_quick_demo_id);
                tvId2.setText("id : " + item.id + "\ncontent : " + item.content + "\nmulitType: " + item.multiType);

                helper.addOnClickListener(R.id.tv_quick_demo_id);
                helper.addOnLongClickListener(R.id.tv_quick_demo_id);
                break;
        }
    }
}
