package cn.dlc.customizedemo.myapplication.RecyclerviewAddItemDecoration.adapter;

import android.support.annotation.NonNull;
import android.widget.TextView;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/02/28.
 * interface by
 */
public class DecorationAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public int getItemLayoutId(int i) {
        return R.layout.item_decoration;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
        TextView btnTv = holder.getText(R.id.tv_btn);
    }
}
