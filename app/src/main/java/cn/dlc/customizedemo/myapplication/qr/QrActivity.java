package cn.dlc.customizedemo.myapplication.qr;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.dlc.customizedemo.myapplication.R;

public class QrActivity extends AppCompatActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;
    @BindView(R.id.light)
    Button mLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
        initQR();
    }

    private void initQR() {
        mQRCodeView.setDelegate(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();//启动相机
        mQRCodeView.startSpot();//启动扫描
        mQRCodeView.startSpotAndShowRect();//显示扫描框，并且延迟1.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        mQRCodeView.closeFlashlight();//关灯
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    //震动器
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    //成功扫描回调
    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e("simon", "成功扫描回调: " + result);
        Toast.makeText(QrActivity.this, "result:" + result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();

    }

    //扫描失败回调
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("simon", "扫描失败回调");
    }

    int lightCode = 0;

    @OnClick(R.id.light)
    public void onViewClicked() {
        if (lightCode == 0) {
            lightCode = 1;
            mQRCodeView.openFlashlight();//开灯
        } else {
            lightCode = 0;
            mQRCodeView.closeFlashlight();

        }
    }
}
