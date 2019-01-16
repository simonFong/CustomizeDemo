package cn.dlc.customizedemo.myapplication.baiduface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/11/13.
 * interface by
 */
public class BaiduFaceActivity extends BaseCommonActivity {
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.btn_recognition)
    Button mBtnRecognition;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_baiduface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.btn_register, R.id.btn_recognition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            case R.id.btn_recognition:
                break;
        }
    }

}
