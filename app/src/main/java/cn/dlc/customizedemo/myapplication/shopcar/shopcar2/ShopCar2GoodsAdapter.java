package cn.dlc.customizedemo.myapplication.shopcar.shopcar2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.shopcar.ShopCarGoodsBean;

public class ShopCar2GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private  List<GoodsBean> mDatas;
    private  Context mContext;

    public ShopCar2GoodsAdapter(Context context, List<GoodsBean> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case GoodsBean.SHOP_DETAIL:
                holder = new ShopViewHolder(from.inflate(R.layout.item_shop_head, parent, false));
                break;
            case GoodsBean.GOOD_DETAIL:
                holder = new GoodViewHolder(from.inflate(R.layout.layout_item_shop_car_goods_1, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        GoodsBean goodsBean = mDatas.get(position);
        return goodsBean.itemType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GoodsBean goodsBean = mDatas.get(position);
        switch (goodsBean.itemType) {
            case GoodsBean.SHOP_DETAIL:
                ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
                shopViewHolder.mTvShopName.setText(goodsBean.shopName);
                break;
            case GoodsBean.GOOD_DETAIL:
                GoodViewHolder goodViewHolder = (GoodViewHolder) holder;
                goodViewHolder.mFlIvSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //头像
                Glide.with(mContext)
                        .load(goodsBean.img)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher)
                                .transforms(new CenterCrop(), new RoundedCorners(mContext.getResources().getDimensionPixelOffset(R
                                        .dimen.normal_10dp))))
                        .into(goodViewHolder.mIvIcon);
                goodViewHolder.mTvTitle.setText(goodsBean.title);
                goodViewHolder.mTvType.setText(goodsBean.type);
                goodViewHolder.mTvPrice.setText("￥" + goodsBean.price);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setNewData(List<GoodsBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void appendData(ArrayList<GoodsBean> mData) {
        mDatas.addAll(mData);
        notifyDataSetChanged();
    }
}
