package com.y.mvp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;
import com.y.mvp.base.BaseActivity;
import com.y.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@TargetApi(Build.VERSION_CODES.N_MR1)
public class ShortCutsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShortCutsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rv)
    RecyclerView rv;
    private ShortCutsAdapter adapter;
    private List<String> selectedCuts = new ArrayList<>();
    private List<String> cuts = new ArrayList<>();
    private List<Integer> icons = new ArrayList<>();
    private ShortcutManager mShortcutManager;

    private String ascii = "算法1", call = "打电话", qq = "QQ", lock = "壁纸";
    private int asciiIcon = R.drawable.cut_ascii;

    @Override
    protected int getLayout() {
        return R.layout.activity_shortcuts;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void init() {
        mToolBar.getTitleView().setText("快捷菜单");
        rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter = new ShortCutsAdapter(R.layout.item_shortcuts, cuts));

        initShortCut();
        initListener();
    }

    private void initShortCut() {
        cuts.add(ascii);
        cuts.add(call);
        cuts.add(qq);
        cuts.add(lock);
        icons.add(asciiIcon);
        icons.add(asciiIcon);
        icons.add(asciiIcon);
        icons.add(asciiIcon);
        mShortcutManager = getSystemService(ShortcutManager.class);
        if (mShortcutManager != null) {
            List<ShortcutInfo> list = mShortcutManager.getDynamicShortcuts();
            for (ShortcutInfo info : list) {
                if (cuts.contains(info.getId())) {
                    selectedCuts.add(info.getId());
                }
            }
        }
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String cut = cuts.get(position);
                if (selectedCuts.contains(cut)) {
                    selectedCuts.remove(cut);
                } else {
                    selectedCuts.add(cut);
                }
                adapter.notifyItemChanged(position);
                selectedChanged();
            }
        });
    }

    private void selectedChanged() {
        int max = mShortcutManager.getMaxShortcutCountPerActivity();
        int size = selectedCuts.size() > max ? max : selectedCuts.size();
        List<ShortcutInfo> infos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Intent intent = null;
            String cut = selectedCuts.get(i);
            if (cut.equals(ascii)) {
                intent = new Intent(this, Arith1Activity.class);
                intent.setAction(Intent.ACTION_VIEW);
            } else if (cut.equals(call)) {
                intent = new Intent(Intent.ACTION_DIAL);
            } else if (cut.equals(qq)) {
                Uri uri = Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=574264083");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setAction(Intent.ACTION_VIEW);
            } else if (cut.equals(lock)) {
                intent = new Intent(Intent.ACTION_SET_WALLPAPER);
            }
            if (intent == null) continue;
            ShortcutInfo info = new ShortcutInfo.Builder(this, cut)
                    .setShortLabel(cut)
                    .setLongLabel(cut)
                    .setIcon(Icon.createWithResource(this, icons.get(cuts.indexOf(cut))))
                    .setDisabledMessage("不存在")
                    .setIntent(intent)
                    .build();
            infos.add(info);
        }
        mShortcutManager.setDynamicShortcuts(infos);
//        mShortcutManager.updateShortcuts(Arrays.asList(info));
//        mShortcutManager.removeDynamicShortcuts(Arrays.asList("id" + index));
    }


    private class ShortCutsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ShortCutsAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            boolean selected = selectedCuts.contains(item);
            helper.setText(R.id.tv_shortcut_name, item);
            helper.setImageResource(R.id.iv_shortcut_icon, icons.get(helper.getLayoutPosition()));
            helper.getView(R.id.iv_shortcut_sure).setVisibility(selected ? View.VISIBLE : View.GONE);
            helper.getConvertView().setBackgroundColor(selected ? ColorUtil.random() : Color.TRANSPARENT);
        }
    }
}
