package cn.dlc.customizedemo.myapplication.SearchHistory;

import android.widget.TextView;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/01/10.
 * interface by
 */
public class SearchRecordAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_search_record;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        TextView recordTv = holder.getText(R.id.tv_record);
        recordTv.setText(getItem(position));
    }
}
