package cn.dlc.customizedemo.myapplication.shopcar.shopcar2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.dlc.customizedemo.myapplication.R;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    public TextView mTvShopName;

    public ShopViewHolder(View itemView) {
        super(itemView);
        mTvShopName = itemView.findViewById(R.id.tv_shop_name);

    }
}
