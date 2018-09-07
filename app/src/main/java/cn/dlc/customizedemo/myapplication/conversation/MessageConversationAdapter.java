package cn.dlc.customizedemo.myapplication.conversation;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.customizedemo.myapplication.R;


/**
 * Created by fengzimin  on  2018/09/07.
 * interface by
 */
public class MessageConversationAdapter extends BaseRecyclerAdapter<MessageConversationBean> {

    private final Context mContext;

    public MessageConversationAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_message_conversation;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        TextView timeTv = holder.getText(R.id.tv_time);

        View leftLl = holder.getView(R.id.ll_left);
        ImageView leftPortraitIv = holder.getImage(R.id.iv_left_portrait);
        TextView leftContentTv = holder.getText(R.id.tv_left_content);

        View rightLl = holder.getView(R.id.ll_right);
        ImageView rightPortraitIv = holder.getImage(R.id.iv_right_portrait);
        TextView rightContentTv = holder.getText(R.id.tv_right_content);

        MessageConversationBean item = getItem(position);
        timeTv.setVisibility(TextUtils.isEmpty(item.time) ? View.GONE : View.VISIBLE);
        timeTv.setText(item.time);


        if (item.showLeft) {
            leftLl.setVisibility(View.VISIBLE);
            rightLl.setVisibility(View.GONE);
            Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher)
                    .transform(new CircleCrop())).load(item.portrait).into(leftPortraitIv);
            leftContentTv.setText(item.content);
        } else {
            leftLl.setVisibility(View.GONE);
            rightLl.setVisibility(View.VISIBLE);
            Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher)
                    .transform(new CircleCrop())).load(item.portrait).into(rightPortraitIv);
            rightContentTv.setText(item.content);
        }


    }
}
