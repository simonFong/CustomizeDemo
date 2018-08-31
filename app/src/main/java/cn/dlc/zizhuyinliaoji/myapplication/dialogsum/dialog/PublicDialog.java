package cn.dlc.zizhuyinliaoji.myapplication.dialogsum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dlc.commonlibrary.utils.DialogUtil;
import cn.dlc.zizhuyinliaoji.myapplication.R;

/**
 * Created by fengzimin  on  2018/07/19.
 * interface by
 */
public class PublicDialog extends Dialog {

    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;

    public TextView mTitleTv;
    public ImageView mStateIv;
    public LinearLayout mContentLl;
    public TextView mContextTv;
    public LinearLayout mBtnsLl;
    public TextView mLeftBtn;
    public TextView mRightBtn;
    private OnBtnClickListener mOnBtnClickListener;
    private static PublicDialog publicDialog;
    public TextView mContentTitleTv;


    public static PublicDialog getPublicDialog(Context context) {
        publicDialog = new PublicDialog(context);
        return publicDialog;
    }

    public PublicDialog(@NonNull Context context) {
        this(context, 0);
    }

    public PublicDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.CommonDialogStyle);
        DialogUtil.adjustDialogLayout(this, true, false);
        DialogUtil.setGravity(this, Gravity.CENTER);
        setContentView(R.layout.dialog_public);
        mTitleTv = findViewById(R.id.tv_title);
        mStateIv = findViewById(R.id.iv_state);
        mContentLl = findViewById(R.id.ll_content);
        mContentTitleTv = findViewById(R.id.tv_content_title);
        mContextTv = findViewById(R.id.tv_content);
        mBtnsLl = findViewById(R.id.ll_btns);
        mLeftBtn = findViewById(R.id.btn_left);
        mRightBtn = findViewById(R.id.btn_right);
    }

    /**
     * 最简单的 只有中间content
     *
     * @param content
     */
    public PublicDialog setMode1(String content) {
        mContextTv.setText(content);
        return publicDialog;
    }

    /**
     * 有标题和内容
     *
     * @param title   标题
     * @param content 内容
     */
    public PublicDialog setMode2(String title, String content) {
        mContentTitleTv.setVisibility(View.VISIBLE);
        mContentTitleTv.setText(title);
        mContextTv.setText(content);
        return publicDialog;
    }

    /**
     * 标题，内容，两个按钮
     *
     * @param title
     * @param content
     * @param leftBtnStr  左键文本
     * @param leftBg      左键的背景
     * @param rightBtnStr 右键文本
     * @param rightBg     右键背景
     */
    public PublicDialog setMode3(String title, String content, String leftBtnStr, int leftBg, String
            rightBtnStr, int rightBg) {
        mTitleTv.setText(title);
        mTitleTv.setVisibility(View.VISIBLE);
        mContextTv.setText(content);
        mBtnsLl.setVisibility(View.VISIBLE);
        //设置按钮背景
        mLeftBtn.setBackgroundResource(leftBg);
        mRightBtn.setBackgroundResource(rightBg);
        //设置按钮文本
        mLeftBtn.setText(leftBtnStr);
        mRightBtn.setText(rightBtnStr);

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBtnClickListener.buttonClick(LEFT_BUTTON);
            }
        });
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBtnClickListener.buttonClick(RIGHT_BUTTON);
            }
        });
        return publicDialog;
    }

    public interface OnBtnClickListener {
        void buttonClick(int leftOrRight);
    }

    public PublicDialog setOnClickListener(OnBtnClickListener onBtnClickListener) {
        this.mOnBtnClickListener = onBtnClickListener;
        return publicDialog;
    }
}
