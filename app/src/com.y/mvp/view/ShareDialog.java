package com.y.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.y.R;
import com.y.util.ScreenUtil;
import com.y.util.T;
import com.y.util.UMShareUtil;

import java.util.ArrayList;
import java.util.List;

public class ShareDialog extends Dialog {

    private Context context;
    private RecyclerView rv;
    private List<Pair<String, Integer>> actions = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private UMShareUtil umShare;

    private void initAction() {
        actions.clear();
        actions.add(new Pair<>("微信", R.mipmap.icon_wxf));
        actions.add(new Pair<>("朋友圈", R.mipmap.icon_wxquan));
        actions.add(new Pair<>("收藏", R.mipmap.share_collect));
        actions.add(new Pair<>("QQ", R.mipmap.icon_qq));
        actions.add(new Pair<>("QQ空间", R.mipmap.qzone_selected));
        actions.add(new Pair<>("微博", R.mipmap.icon_wbo));
        actions.add(new Pair<>("支付宝", R.drawable.umeng_socialize_alipay));
        actions.add(new Pair<>("钉钉", R.drawable.umeng_socialize_ding));
        actions.add(new Pair<>("短信", R.drawable.umeng_socialize_sms));
    }


    public ShareDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context = context;
    }


    public ShareDialog action(UMShareUtil umShare) {
        this.umShare = umShare;
        return this;
    }

    private void share(){
        dismiss();
        umShare.callBack(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                T.show("分享出错 : " + throwable.getLocalizedMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        }).share();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_menu);
        initAction();
        init();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (umShare == null) return;
                switch (position) {
                    case 0:
                        umShare.wx();
                        break;
                    case 1:
                        umShare.wx_circle();
                        break;
                    case 2:
                        umShare.wx_favor();
                        break;
                    case 3:
                        umShare.qq();
                        break;
                    case 4:
                        umShare.qzone();
                        break;
                    case 5:
                        umShare.sina();
                        break;
                    case 6:
                        umShare.ali();
                        break;
                    case 7:
                        umShare.ding();
                        break;
                    case 8:
                        umShare.sms();
                        break;
                    default:
                        umShare.wx();
                        break;
                }
                share();
            }
        });
    }

    private void init() {
        rv = findViewById(R.id.rv);
        GridLayoutManager glManager = new GridLayoutManager(context, 5);
        rv.setLayoutManager(glManager);
        rv.setAdapter(adapter = new BaseQuickAdapter<Pair<String, Integer>, BaseViewHolder>(R.layout.item_share, actions) {
            @Override
            protected void convert(BaseViewHolder helper, Pair<String, Integer> item) {
                helper.setText(R.id.tv, item.first);
                helper.setImageResource(R.id.iv, item.second);
                helper.addOnClickListener(R.id.iv);
            }

        });

    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenUtil.getScreenWidth();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
    }
}
