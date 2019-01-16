package cn.dlc.customizedemo.myapplication.SerialPort;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.dlc.dlcconversiontool.HexTool;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/12/26.
 * interface by
 */
public class SerialPortActivity extends BaseCommonActivity {
    private static int FLOWING_WATER;
    @BindView(R.id.tv_order)
    TextView mTvOrder;
    @BindView(R.id.tv_flowing_water)
    TextView mTvFlowingWater;
    @BindView(R.id.tv_xor)
    TextView mTvXor;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_serial_port;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        getdata();
    }

    private void getdata() {
        String flowingWater = getFlowingWater();
        mTvFlowingWater.setText(flowingWater);
        String xorHex = HexTool.getInstance().getXOR("3BB304A1" + flowingWater + "0000");
        mTvXor.setText(xorHex);
        String orderPre = "3BB304A1" + flowingWater + "0000" + xorHex;
        mTvOrder.setText(orderPre);
    }

    public static String getFlowingWater() {
        if (FLOWING_WATER >= 255) {
            FLOWING_WATER = 0;
        } else {
            FLOWING_WATER++;
        }
        return ByteUtil.decimal2fitHex(FLOWING_WATER, 2);
    }
}
