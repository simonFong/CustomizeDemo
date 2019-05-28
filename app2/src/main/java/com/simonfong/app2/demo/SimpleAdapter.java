package com.simonfong.app2.demo;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.simonfong.app2.R;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;

/**
 * Created  on  2019/05/27.
 * interface by
 *
 * @author fengzimin
 */
public class SimpleAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public int getItemLayoutId(int i) {
        return R.layout.item_simple;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
        TextView text = holder.getText(R.id.text);
        text.setText(getItem(position));
    }
}
