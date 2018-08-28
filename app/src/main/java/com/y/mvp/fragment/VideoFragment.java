package com.y.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.y.R;
import com.y.adapter.VideoAdapter;
import com.y.bean.Video;
import com.y.mvp.activity.VideoActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.VideoPresenter;
import com.y.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.Vitamio;

public class VideoFragment extends BaseFragment<VideoPresenter> {

    String path = "http://gslb.miaopai.com/stream/3D~8BM-7CZqjZscVBEYr5g__.mp4";

    public static VideoFragment newInstance(){
        VideoFragment chat = new VideoFragment();
        return chat;
    }

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    private VideoAdapter videoAdapter;
    private List<Video> videoList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(AppUtil.context());
    }

    @Override
    protected void init() {
        LinearLayoutManager llM = new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false);
        rvVideo.setLayoutManager(llM);
        videoAdapter = new VideoAdapter(R.layout.item_video,videoList);
        rvVideo.setAdapter(videoAdapter);

        for (int i = 0; i < 8; i++) {
            Video video = new Video();
            video.path = path;
            videoList.add(video);
        }
        videoAdapter.notifyDataSetChanged();

        initListener();
    }

    private void initListener(){
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoActivity.start(mActivity);
            }
        });
    }
}
