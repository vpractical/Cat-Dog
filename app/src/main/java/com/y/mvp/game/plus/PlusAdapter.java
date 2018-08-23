package com.y.mvp.game.plus;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.y.R;

import java.util.ArrayList;
import java.util.List;

public class PlusAdapter extends BaseQuickAdapter<Plus, PlusAdapter.ItemHolder> {

    public interface OnPlusChangedListener {
        void onChanged(List<Plus> selectedList);
    }

    private OnPlusChangedListener changedListener;

    public void setOnPlusChangedListener(OnPlusChangedListener l) {
        this.changedListener = l;
    }

    private List<Plus> data;
    private List<Plus> selectedList = new ArrayList<>();
    private List<Plus> selectedClone = new ArrayList<>();

    public PlusAdapter(int layoutResId, @Nullable List<Plus> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final ItemHolder helper, final Plus item) {
        View root = helper.getView(R.id.root);
        TextView tvName = helper.getView(R.id.name);
        final EditText etCount = helper.getView(R.id.count);
        tvName.requestFocus();
        tvName.setText(item.name);
        etCount.setText(item.count + "");
        if (selectedList.contains(item)) {
            root.setBackgroundColor(Color.parseColor(item.c));
            tvName.setTextColor(Color.BLACK);
            etCount.setTextColor(Color.BLACK);
        } else {
            root.setBackgroundColor(Color.TRANSPARENT);
            tvName.setTextColor(Color.WHITE);
            etCount.setTextColor(Color.WHITE);
        }

        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etCount.hasFocus()){
                    int num = 1;
                    if(s.toString().length() > 0){
                        num = Integer.parseInt(s.toString().replace(" ", ""));
                        if (num < 1) {
                            num = 1;
                        }
                    }
                    if(item.count != num){
                        item.count = num;
                    }
                }
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Plus clone = item.clone();
                    if (selectedClone.size() > 0 && selectedClone.get(selectedClone.size() - 1).name.equals(clone.name)) {
                        selectedClone.get(selectedClone.size() - 1).count += item.count;
                    } else {
                        selectedClone.add(clone);
                    }
                    notifyItemChanged(helper.getLayoutPosition());
                    if (changedListener != null) {
                        changedListener.onChanged(selectedClone);
                    }

                    if(!selectedList.contains(item)){
                        selectedList.add(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clear() {
        for (Plus p : data) {
            p.count = 1;
        }
        selectedList.clear();
        selectedClone.clear();
        notifyDataSetChanged();
        if (changedListener != null) {
            changedListener.onChanged(selectedClone);
        }
    }

    protected class ItemHolder extends BaseViewHolder {

        public ItemHolder(View view) {
            super(view);
        }
    }
}
