package cn.dlc.customizedemo.myapplication.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.pay.ui.PayBar;

public class PayActivity extends AppCompatActivity {

    @BindView(R.id.paybar_wechat)
    PayBar mPaybarWechat;
    @BindView(R.id.paybar_alipay)
    PayBar mPaybarAlipay;
    @BindView(R.id.paybar_card)
    PayBar mPaybarCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.paybar_wechat, R.id.paybar_alipay, R.id.paybar_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paybar_wechat:
                payType = 1;
                setPayType();

                break;
            case R.id.paybar_alipay:
                payType = 2;
                setPayType();
                break;

            case R.id.paybar_card:
                payType = 3;
                setPayType();
                break;
        }
    }

    int payType = 1;

    private void setPayType() {
        mPaybarWechat.setSelect(payType == 1);
        mPaybarAlipay.setSelect(payType == 2);
        mPaybarCard.setSelect(payType == 3);

    }

}
