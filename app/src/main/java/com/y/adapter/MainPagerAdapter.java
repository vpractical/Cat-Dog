package com.y.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.y.mvp.base.BaseFragment;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;

    public MainPagerAdapter(FragmentManager fm,List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
