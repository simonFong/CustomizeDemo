package cn.dlc.customizedemo.myapplication.shopcar;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.shopcar.shopcar1.ShopCarActivity;
import cn.dlc.customizedemo.myapplication.shopcar.shopcar2.ShopCar2Activity;

public class SelectShopCarActivity extends BaseCommonActivity {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_select_shop_car;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(ShopCarActivity.class);
                break;
            case R.id.button2:
                startActivity(ShopCar2Activity.class);
                break;
        }
    }
}
