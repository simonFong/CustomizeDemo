package cn.dlc.customizedemo.myapplication.pay.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/07/12.
 * interface by
 */
public class PayBar extends LinearLayout {


    private Context mContext;
    private int mIcon;
    private String mName;
    private int mSelectBg;
    private int mCommonType;
    private ImageView mSelectIv;
    private float mNameSize;
    private int mNameColor;

    public PayBar(Context context) {
        super(context);
    }

    public PayBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PayBar);
        mIcon = typedArray.getResourceId(R.styleable.PayBar_icon, R.mipmap.ic_launcher);
        mName = typedArray.getString(R.styleable.PayBar_name);
        mNameSize = typedArray.getDimension(R.styleable.PayBar_name_size, -1);
        mNameColor = typedArray.getColor(R.styleable.PayBar_name_color, -1);
        mSelectBg = typedArray.getResourceId(R.styleable.PayBar_select_bg, R.drawable
                .selector_bg_select);
        mCommonType = typedArray.getInteger(R.styleable.PayBar_common_type, -1);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_pay_bar, this, true);
        ImageView iconIv = inflate.findViewById(R.id.iv_icon);
        TextView nameTv = inflate.findViewById(R.id.tv_name);

        mSelectIv = inflate.findViewById(R.id.iv_select);
        mSelectIv.setBackgroundResource(mSelectBg);
        nameTv.setText(mName);
        if (mNameColor != -1) {
            nameTv.setTextColor(mNameColor);
        }
        if (mNameSize != -1) {
            nameTv.setTextSize(mNameSize);
        }

        switch (mCommonType) {
            case -1:
                iconIv.setBackgroundResource(mIcon);
                break;
            case 1://微信
                iconIv.setBackgroundResource(R.mipmap.ic_wechat);
                nameTv.setText("微信");
                break;
            case 2://支付宝
                iconIv.setBackgroundResource(R.mipmap.ic_alipay);
                nameTv.setText("支付宝");
                break;
        }


    }

    public void setSelect(boolean isSelect) {
        mSelectIv.setSelected(isSelect);
    }
}
