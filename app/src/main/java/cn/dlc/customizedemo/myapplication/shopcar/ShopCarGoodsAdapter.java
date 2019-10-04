package cn.dlc.customizedemo.myapplication.shopcar;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.licheedev.myutils.LogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.util.BigDecimalUtil;

import static cn.dlc.commonlibrary.utils.ResUtil.getResources;

/**
 * Created by fengzimin  on  2019/01/08.
 * interface by
 */
public class ShopCarGoodsAdapter extends BaseRecyclerAdapter<ShopCarGoodsBean> {

    private final Context mContext;
    private ShopCarGoodsItemAdapter mShopCarGoodsItemAdapter;
    private OnShopCarGoodsAdapterListener mListener;
    private boolean isAddItemDecoration = false;

    public ShopCarGoodsAdapter(Context context) {
        mContext = context;
        isAddItemDecoration = false;

    }

    @Override
    public void setNewData(List<? extends ShopCarGoodsBean> data) {
        super.setNewData(data);
        setAllSelect(false);
    }

    @Override
    public int getItemViewType(int position) {
        LogPlus.e("getItemViewType-----------"+position+"----------"+super.getItemViewType(position));
        return super.getItemViewType(position);

    }

    @Override
    public void appendData(List<? extends ShopCarGoodsBean> data) {
        super.appendData(data);
        if (isAllSelectState) {
            setAllSelect(true);
        }
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_shop_car_goods;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        TextView shopNameTv = holder.getText(R.id.tv_shop_name);
        TextView contactShopTv = holder.getText(R.id.tv_contact_shop);
        RecyclerView recyclerview = holder.getView(R.id.recyclerview);

        initRecycler(recyclerview);

        final ShopCarGoodsBean item = getItem(position);
        shopNameTv.setText(item.shopName);
        mShopCarGoodsItemAdapter.setNewData(item.list);

        contactShopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.PRIVATE, item.rongId, item
//                        .shopName);
            }
        });

    }


    private String sumPrice = "0";//总价格
    private int sumNum = 0;//总数量
    private boolean isAllSelectState = false;//是否全选状态
    private List<Map<Integer, Integer>> selectPositionMap = new ArrayList<>();

    /**
     * 判断是否全是选中状态,获取总价格，获取总数量
     *
     * @return
     */
    public void getAllSelectState() {
        sumPrice = "0.00";
        sumNum = 0;
        isAllSelectState = true;
        for (int i = 0; i < mData.size(); i++) {
            List<ShopCarGoodsItemBean> list = mData.get(i).list;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).isSelect != true) {
                    isAllSelectState = false;
                } else {
                    ShopCarGoodsItemBean bean = list.get(j);
                    sumNum = sumNum + list.get(j).num;
                    String mul = BigDecimalUtil.mul(bean.price, String.valueOf(bean.num), 2);
                    sumPrice = BigDecimalUtil.add(sumPrice, mul, 2);
                    HashMap<Integer, Integer> integerHashMap = new HashMap<>();
                    integerHashMap.put(i, j);
                    selectPositionMap.add(integerHashMap);
                }
            }
        }
        mListener.setAllSelect(isAllSelectState, sumPrice, sumNum);
    }

    /**
     * 设置全选或全不选
     */
    public void setAllSelect(boolean isAllSelect) {
        sumPrice = "0.00";
        sumNum = 0;
        isAllSelectState = true;
        for (int i = 0; i < mData.size(); i++) {
            List<ShopCarGoodsItemBean> list = mData.get(i).list;
            for (int j = 0; j < list.size(); j++) {
                list.get(j).isSelect = isAllSelect;
                isAllSelectState = isAllSelect;
                if (isAllSelect) {
                    ShopCarGoodsItemBean bean = list.get(j);
                    sumNum = sumNum + list.get(j).num;
                    String mul = BigDecimalUtil.mul(bean.price, String.valueOf(bean.num), 2);
                    sumPrice = BigDecimalUtil.add(sumPrice, mul, 2);
                }
            }
        }
        notifyDataSetChanged();
        mListener.setAllSelect(isAllSelectState, sumPrice, sumNum);
    }

    private void initRecycler(RecyclerView recyclerview) {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_decoration_line));
        if (!isAddItemDecoration) {
            recyclerview.addItemDecoration(dividerItemDecoration);
            isAddItemDecoration = true;
        }
        mShopCarGoodsItemAdapter = new ShopCarGoodsItemAdapter(mContext);
        recyclerview.setAdapter(mShopCarGoodsItemAdapter);
    }

    public interface OnShopCarGoodsAdapterListener {
        void setAllSelect(boolean allSelectState, String sumPrice, int sumNum);//判断是否全选状态
    }

    public void setOnShopCarGoodsAdapterListener(OnShopCarGoodsAdapterListener listener) {
        mListener = listener;
    }

/*-----------------------------------------------------------------------------------------------------------------------------*/

    class ShopCarGoodsItemAdapter extends BaseRecyclerAdapter<ShopCarGoodsItemBean> {

        private final Context mContext;

        public ShopCarGoodsItemAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.layout_item_shop_car_goods;
        }

        @Override
        public void onBindViewHolder(CommonHolder holder, final int position) {
            final FrameLayout selectIv = holder.getView(R.id.iv_select);
            TextView titleTv = holder.getText(R.id.tv_title);
            TextView typeTv = holder.getText(R.id.tv_type);
            ImageView iconIv = holder.getImage(R.id.iv_icon);
            TextView priceTv = holder.getText(R.id.tv_price);
            SelectNumView selectNum = holder.getView(R.id.snv_select_num);

            ShopCarGoodsItemBean item = getItem(position);
            //头像
            Glide.with(mContext)
                    .load(item.img)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher)
                            .transforms(new CenterCrop(), new RoundedCorners(mContext.getResources().getDimensionPixelOffset(R
                                    .dimen.normal_10dp))))
                    .into(iconIv);

            //标题
            titleTv.setText(item.title);
            //类型
            typeTv.setText(item.type);
            //价格
            priceTv.setText("￥" + item.price);
            //数量
            selectNum.setNum(item.num);
            selectNum.setMaxNum(99);
            selectNum.setOnSelectNumViewListener(new SelectNumView.OnSelectNumViewListener() {
                @Override
                public void add(int num) {
                    getItem(position).num = num;
                    getAllSelectState();
                }

                @Override
                public void less(int num) {
                    getItem(position).num = num;
                    getAllSelectState();
                }
            });

            selectIv.setSelected(item.isSelect);

            selectIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(position).isSelect = !getItem(position).isSelect;
                    notifyDataSetChanged();
                    getAllSelectState();
                }
            });
        }

    }


}
