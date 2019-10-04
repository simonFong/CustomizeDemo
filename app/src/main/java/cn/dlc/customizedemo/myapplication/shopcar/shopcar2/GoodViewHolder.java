package cn.dlc.customizedemo.myapplication.shopcar.shopcar2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.shopcar.SelectNumView;

public class GoodViewHolder extends RecyclerView.ViewHolder {

    public FrameLayout mFlIvSelect;
    public ImageView mIvIcon;
    public TextView mTvTitle;
    public TextView mTvType;
    public TextView mTvPrice;
    public SelectNumView mSnvSelectNum;

    public GoodViewHolder(View itemView) {
        super(itemView);
        mFlIvSelect = itemView.findViewById(R.id.iv_select);
        mIvIcon = itemView.findViewById(R.id.iv_icon);
        mTvTitle = itemView.findViewById(R.id.tv_title);
        mTvType = itemView.findViewById(R.id.tv_type);
        mTvPrice = itemView.findViewById(R.id.tv_price);
        mSnvSelectNum = itemView.findViewById(R.id.snv_select_num);
    }

}
