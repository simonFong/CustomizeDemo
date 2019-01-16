package cn.dlc.customizedemo.myapplication.shopcar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dlc.customizedemo.myapplication.R;


/**
 * Created by fengzimin  on  2019/01/08.
 * interface by
 */
public class SelectNumView extends LinearLayout implements View.OnClickListener {

    private String TAG = "SelectNumView";
    private Context mContext;
    private int num = 1;
    private TextView mNumTv;
    private OnSelectNumViewListener mListener;
    private int maxNum = 99;

    public SelectNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.view_select_num, this, true);
        ImageView lessIv = inflate.findViewById(R.id.iv_less);
        ImageView addIv = inflate.findViewById(R.id.iv_add);
        View lessFl = inflate.findViewById(R.id.fl_less);
        View addFl = inflate.findViewById(R.id.fl_add);
        mNumTv = inflate.findViewById(R.id.tv_num);
        lessFl.setOnClickListener(this);
        addFl.setOnClickListener(this);
        lessIv.setOnClickListener(this);
        addIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_less:
                Log.e(TAG, "fl_less");
                if (num == 1) {
                    return;
                }
                num--;
                setNum(num);
                if (mListener != null)
                    mListener.less(getNum());
                break;
            case R.id.fl_add:
                Log.e(TAG, "fl_add");
                if (num == maxNum) {
                    return;
                }
                num++;
                setNum(num);
                if (mListener != null) {
                    mListener.add(getNum());
                }
                break;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        mNumTv.setText(num + "");
    }

    public void setMaxNum(int num) {
        this.maxNum = maxNum;
    }

    public interface OnSelectNumViewListener {
        void add(int num);

        void less(int num);
    }

    public void setOnSelectNumViewListener(OnSelectNumViewListener listener) {
        mListener = listener;
    }
}
