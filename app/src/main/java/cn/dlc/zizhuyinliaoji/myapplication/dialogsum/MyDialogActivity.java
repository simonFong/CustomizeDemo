package cn.dlc.zizhuyinliaoji.myapplication.dialogsum;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.zizhuyinliaoji.myapplication.R;
import cn.dlc.zizhuyinliaoji.myapplication.dialogsum.dialog.PublicDialog;
import cn.dlc.zizhuyinliaoji.myapplication.dialogsum.dialog.ShowCenterDialog;

public class MyDialogActivity extends BaseCommonActivity {

    @BindView(R.id.btn_dialog1)
    Button mBtnDialog1;
    @BindView(R.id.btn_dialog2)
    Button mBtnDialog2;
    @BindView(R.id.btn_dialog3)
    Button mBtnDialog3;
    @BindView(R.id.btn_dialog4)
    Button mBtnDialog4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_dialog;
    }


    @OnClick({R.id.btn_dialog1, R.id.btn_dialog2, R.id.btn_dialog3, R.id.btn_dialog4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog1:
                ShowCenterDialog showCenterDialog = new ShowCenterDialog(this);
                showCenterDialog.setCancelable(true);
                showCenterDialog.show();
                break;
            case R.id.btn_dialog2:
                PublicDialog.getPublicDialog(this).setMode1("只有内容").show();

                break;
            case R.id.btn_dialog3:
                PublicDialog publicDialog = PublicDialog.getPublicDialog(this).setMode2("提示",
                        "已经发送了通知，请耐心等候消息");
                publicDialog.mContentLl.setGravity(Gravity.CENTER_HORIZONTAL);

                publicDialog.show();
                break;
            case R.id.btn_dialog4:
                PublicDialog.getPublicDialog(this).setMode3("提示", "是否开始完成任务", "取消", 0, "确定", 0)
                        .setOnClickListener
                                (new PublicDialog.OnBtnClickListener() {

                                    @Override
                                    public void buttonClick(int leftOrRight) {
                                        switch (leftOrRight) {
                                            case PublicDialog.LEFT_BUTTON:
                                                Toast.makeText(MyDialogActivity.this, "按了左键",
                                                        Toast.LENGTH_SHORT).show();
                                                break;
                                            case PublicDialog.RIGHT_BUTTON:
                                                Toast.makeText(MyDialogActivity.this, "按了右键",
                                                        Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                }).show();

                break;
        }
    }
}
