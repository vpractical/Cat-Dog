package com.y.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.y.R;
import com.y.bean.Role;
import com.y.imageloader.ImageLoader;

import java.util.List;

public class RoleAdapter extends PagerAdapter {

    private Activity activity;
    private List<Role> roleList;

    public RoleAdapter(Activity activity, List<Role> list){
        this.activity = activity;
        this.roleList = list;
    }


    @Override
    public int getCount() {
        return roleList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Role item = roleList.get(position);
        ImageView iv = new ImageView(activity);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                ImageLoader
                .with(activity)
                .url(item.path)
                .placeHolder(R.drawable.chrysanthemum_1)
                .errorHolder(R.drawable.chat_dark)
                .skipMemoryCache(false)
                .skipDiskCache(false)
                .into(iv);

        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
