package cn.dlc.zizhuyinliaoji.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.zizhuyinliaoji.myapplication.MPAndroidChart.ChartActivity;
import cn.dlc.zizhuyinliaoji.myapplication.addImage.EvaluateActivity;
import cn.dlc.zizhuyinliaoji.myapplication.map.MapActivity;
import cn.dlc.zizhuyinliaoji.myapplication.dialogsum.MyDialogActivity;
import cn.dlc.zizhuyinliaoji.myapplication.pay.PayActivity;
import cn.dlc.zizhuyinliaoji.myapplication.qr.QrActivity;
import cn.dlc.zizhuyinliaoji.myapplication.qr.QrCreateActivity;
import cn.dlc.zizhuyinliaoji.myapplication.weather.WeatherActivity;
import cn.dlc.zizhuyinliaoji.myapplication.eventbus.activity.EventbusActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_dialog)
    Button mTvDialog;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_dialog, R.id.btn_login, R.id.weather, R.id.map, R.id.pay, R.id.qr_scan, R
            .id.qr_create, R.id.friend_circle, R.id.event_bus, R.id.super_circle, R.id.mpAndroid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog:
                startActivity(MyDialogActivity.class);
                break;
            case R.id.btn_login:
                break;
            case R.id.weather:
                startActivity(WeatherActivity.class);
                break;
            case R.id.map:
                startActivity(MapActivity.class);
                break;
            case R.id.pay:
                startActivity(PayActivity.class);
                break;
            case R.id.qr_scan:
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        //说明已经获取到摄像头权限了 想干嘛干嘛
                        startActivity(QrActivity.class);
                    }
                } else {
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    startActivity(QrActivity.class);
                }
                break;
            case R.id.qr_create:
                startActivity(QrCreateActivity.class);
                break;
            case R.id.friend_circle:
                startActivity(EvaluateActivity.class);
                break;
            case R.id.event_bus:
                startActivity(EventbusActivity.class);
                break;
            case R.id.super_circle:

                break;
            case R.id.mpAndroid:
                startActivity(ChartActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }
}
