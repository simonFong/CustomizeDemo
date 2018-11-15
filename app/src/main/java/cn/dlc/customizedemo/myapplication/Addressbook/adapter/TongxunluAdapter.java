package cn.dlc.customizedemo.myapplication.Addressbook.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.customizedemo.myapplication.Addressbook.bean.Contact;
import cn.dlc.customizedemo.myapplication.R;


/**
 * Created by Administrator on 2018/3/26.
 */

public class TongxunluAdapter extends BaseRecyclerAdapter<Contact> {

    private final Context mContext;
    private OnClickListener mOnClickListener;
    private List<Contact> beanList2 = new ArrayList<>();

    public TongxunluAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_tongxunlu_layout;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, final int position) {
        final ImageView selectIv = holder.getImage(R.id.item_tougxunlu_select_iv);
        ImageView touxiangIv = holder.getImage(R.id.item_tongxunlu_touxiang_iv);
        TextView nameTv = holder.getText(R.id.item_tougxunlu_name_tv);
        TextView juliTv = holder.getText(R.id.item_tongxunlu_juli_tv);
        View itemLl = holder.getView(R.id.item_tongxunlu_ll);
        Contact item = getItem(position);

        //        if (item.isSelect()) {
        //            selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.xuanzhong));
        //        } else {
        //            selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.weixuanzhong));
        //        }

        Glide.with(mContext).load(ResUtil.getDrawable(R.mipmap.ic_launcher)).apply(new RequestOptions().diskCacheStrategy
                (DiskCacheStrategy.ALL).transform(new CircleCrop())).into(touxiangIv);

        nameTv.setText(item.getContactName());
//        String distance = Distance.getDistance(UserHelper.getInstance().getAccountEntityLon(), UserHelper.getInstance()
//                .getAccountEntityLat(), item.getLon(), item.getLat());
//        juliTv.setText("[" + distance + "km]");

        //        setOnItemClickListener(new OnItemClickListener() {
        //            @Override
        //            public void onItemClick(ViewGroup parent, CommonHolder holder, int position) {
        //                getItem(position).setSelect(!getItem(position).isSelect());
        //                notifyDataSetChanged();
        //            }
        //        });

//        itemLl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (item.isSelect()) {
//                    item.setSelect(!item.isSelect());
//                    beanList2.remove(item);
//                } else {
//                    item.setSelect(!item.isSelect());
//                    beanList2.add(item);
//                }
//                notifyDataSetChanged();
//                mOnClickListener.select(position, beanList2);
//            }
//        });

    }

    /**
     * 选中添加进来的数据
     */
    public List<Contact> getAddData() {
        if (beanList2 != null) {
            return beanList2;
        } else {
            return new ArrayList<>();
        }
    }

    public interface OnClickListener {
        void select(int positon, List beanList);
    }

    public void setOnClickListener(OnClickListener mOnClickChildListener) {
        this.mOnClickListener = mOnClickChildListener;
    }
}
