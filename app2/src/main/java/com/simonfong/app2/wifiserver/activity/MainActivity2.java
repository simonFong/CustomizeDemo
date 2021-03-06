package com.simonfong.app2.wifiserver.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.simonfong.app2.R;
import com.simonfong.app2.wifiserver.BaseActivity;


/**
 * 作者：chenZY
 * 时间：2018/4/3 14:56
 * 描述：https://www.jianshu.com/u/9df45b87cfdf
 * https://github.com/leavesC
 */
public class MainActivity2 extends BaseActivity {

    private static final int CODE_REQ_PERMISSIONS = 665;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQ_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    showToast("缺少权限，请先授予权限");
                    return;
                } else {
                    showToast("已获得权限");
                }
            }
        }
    }

    public void checkPermission(View view) {
        ActivityCompat.requestPermissions(MainActivity2.this,
                new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_REQ_PERMISSIONS);
    }

    public void startFileSenderActivity(View view) {
        startActivity(FileSenderActivity.class);
    }

    public void startFileReceiverActivity(View view) {
//        startActivity(FileReceiverActivity.class);
    }

}
