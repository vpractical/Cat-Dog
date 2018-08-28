package com.y.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.bean.Video;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<Video,BaseViewHolder>{

    public VideoAdapter(int layoutResId, @Nullable List<Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        ImageView vv = helper.getView(R.id.iv_video_convert);
        vv.setImageResource(R.drawable.bg_window);

    }
}
