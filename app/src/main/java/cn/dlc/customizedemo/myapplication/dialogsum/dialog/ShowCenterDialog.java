package cn.dlc.customizedemo.myapplication.dialogsum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.dlc.commonlibrary.utils.DialogUtil;
import cn.dlc.commonlibrary.utils.ToastUtil;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/5/28.
 * interface by
 */
public class ShowCenterDialog extends Dialog {
    private Context mContext;
    private TextView mCancelTv;
    private TextView mSureTv;
    private EditText mOrganizationNameEt;

    public ShowCenterDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ShowCenterDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.CommonDialogStyle);
        mContext = context;
        DialogUtil.adjustDialogLayout(this, true, false);
        DialogUtil.setGravity(this, Gravity.CENTER);
        setContentView(R.layout.dialog_create_organization);
        mOrganizationNameEt = findViewById(R.id.et_organization_name);
        mCancelTv = findViewById(R.id.tv_cancel);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSureTv = findViewById(R.id.tv_sure);
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showOne(mContext, mOrganizationNameEt.getText().toString());
            }
        });

    }
}
