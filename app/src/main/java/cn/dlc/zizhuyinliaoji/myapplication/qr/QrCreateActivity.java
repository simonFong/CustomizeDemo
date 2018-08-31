package cn.dlc.zizhuyinliaoji.myapplication.qr;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.dlc.zizhuyinliaoji.myapplication.R;

public class QrCreateActivity extends AppCompatActivity {

    @BindView(R.id.et_edit)
    EditText mEtEdit;
    @BindView(R.id.btn_create_qr)
    Button mBtnCreateQr;
    @BindView(R.id.iv_code)
    ImageView mIvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_create);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_create_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_create_qr:
                String s = mEtEdit.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    s = "我是生成二维码";
                }
                // 生成二维码
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(s, BGAQRCodeUtil.dp2px
                        (QrCreateActivity.this, 150), Color.parseColor("#ff0000"), null);
                Glide.with(QrCreateActivity.this).load(bitmap).into(mIvCode);
                break;

        }
    }
}
