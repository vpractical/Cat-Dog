package com.y.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.y.R;
import com.y.adapter.VideoAdapter;
import com.y.bean.HotStraetgyEntity;
import com.y.mvp.activity.video.VideoActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.presenter.VideoContract;
import com.y.mvp.fragment.presenter.VideoPresenter;
import com.y.util.AppUtil;
import com.y.util.L;
import com.y.widget.PageSwitchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.Vitamio;

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View{

    public static VideoFragment newInstance(){
        VideoFragment chat = new VideoFragment();
        return chat;
    }

    private static final String TAG = "VideoFragment";
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    private int currentPage = 1;
    @BindView(R.id.container)
    PageSwitchView switchView;
    private VideoAdapter videoAdapter;
    private List<HotStraetgyEntity.ItemListEntity> videoList = new ArrayList<>();

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
        videoAdapter = new VideoAdapter(R.layout.item_video_hot,videoList);
        videoAdapter.setUpFetchEnable(true);
        rvVideo.setAdapter(videoAdapter);
        initListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && videoList.isEmpty()){
            switchView.showLoadingView();
            load(currentPage = 1);
        }
    }

    private void initListener(){
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotStraetgyEntity.ItemListEntity.DataEntity data = videoList.get(position).getData();
                VideoActivity.start(mActivity,data.getPlayUrl(),data.getDescription());
            }
        });
//        videoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                load(currentPage + 1);
//            }
//        },rvVideo);
//        videoAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
//            @Override
//            public void onUpFetch() {
//                videoAdapter.setUpFetching(true);
//                load(1);
//            }
//        });
//
        switchView.setOnErrorReloadListener(new PageSwitchView.OnErrorReloadListener() {
            @Override
            public void onReloadClick() {
                switchView.showLoadingView();
                load(currentPage + 1);
            }
        });
    }

    private void load(int page){
        L.e("load : " + page);
        mPresenter.load(page);
    }

    @Override
    public void loadSuccess(int page, List<HotStraetgyEntity.ItemListEntity> list) {
        if(page == 1){
            videoList.clear();
            videoAdapter.setUpFetching(false);
        }

        videoList.addAll(list);

        if(list.isEmpty()){
            videoAdapter.loadMoreEnd();
        }else{
            videoAdapter.loadMoreComplete();
            currentPage = page;
        }

        if(!videoList.isEmpty()){
            switchView.showDataView(rvVideo);
            videoAdapter.notifyDataSetChanged();
        }else{
            switchView.showEmptyView();
        }
    }

    @Override
    public void loadFailed(final int page, String msg) {
        if(currentPage == 1){
            switchView.showErrorView();
        }
        videoAdapter.loadMoreFail();
    }
}
