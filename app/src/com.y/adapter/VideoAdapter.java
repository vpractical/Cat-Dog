package com.y.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.bean.HotStraetgyEntity;
import com.y.imageloader.ImageLoader;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<HotStraetgyEntity.ItemListEntity,BaseViewHolder>{

    public VideoAdapter(int layoutResId, @Nullable List<HotStraetgyEntity.ItemListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotStraetgyEntity.ItemListEntity item) {
        helper.setText(R.id.tv_title, item.getData().getTitle());
        //获取时间
        int duration = item.getData().getDuration();
        int mm = duration / 60;//分
        int ss = duration % 60;//秒
        String second = "";//秒
        String minute = "";//分
        if (ss < 10) {
            second = "weather0" + String.valueOf(ss);
        } else {
            second = String.valueOf(ss);
        }
        if (mm < 10) {
            minute = "weather0" + String.valueOf(mm);
        } else {
            minute = String.valueOf(mm);//分钟
        }
        helper.setText(R.id.tv_time, "#" + item.getData().getCategory() + " / " + minute + "'" + second + '"');

        ImageLoader.with(mContext)
                .url(Uri.parse(item.getData().getCover().getFeed()))
                .placeHolder(R.drawable.loading)
                .errorHolder(R.drawable.chat_dark)
                .into((ImageView) helper.getView(R.id.iv));
    }
}
