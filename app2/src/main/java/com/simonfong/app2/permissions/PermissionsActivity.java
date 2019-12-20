package com.simonfong.app2.permissions;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.simonfong.app2.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/12/4.
 * interface by
 */
public class PermissionsActivity extends BaseCommonActivity {
    @BindView(R.id.btn_request)
    Button mBtnRequest;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_permissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            Toast.makeText(PermissionsActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void forbitPermissons() {
            //            finish();
            Toast.makeText(PermissionsActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn_request)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request :
                //两个日历权限和一个数据读写权限
                String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
                //这里的this不是上下文，是Activity对象！
                PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
                break;
        }
    }
}
