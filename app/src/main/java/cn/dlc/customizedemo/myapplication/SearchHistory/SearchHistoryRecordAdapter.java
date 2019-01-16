package cn.dlc.customizedemo.myapplication.SearchHistory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/01/10.
 * interface by
 */
public class SearchHistoryRecordAdapter extends BaseRecyclerAdapter<String> implements View.OnClickListener {

    private OnSearchHistoryRecordAdapterListener mListener;

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_search_record_history;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        TextView historyTv = holder.getText(R.id.tv_history);
        ImageView deleteIv = holder.getImage(R.id.iv_delete);
        historyTv.setText(getItem(position));
        deleteIv.setTag(position);
        deleteIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int position = (int) v.getTag();
            mListener.deleteItemHistory(position);
        }
    }

    public interface OnSearchHistoryRecordAdapterListener {
        void deleteItemHistory(int position);
    }

    public void setOnSearchHistoryRecordAdapterListener(OnSearchHistoryRecordAdapterListener listener) {
        mListener = listener;
    }
}
