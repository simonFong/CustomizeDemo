package com.simonfong.app2.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.simonfong.app2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created  on  2019/05/27.
 * interface by
 *
 * @author fengzimin
 */
public class DemoActivity extends BaseCommonActivity {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private static int FLOWING_WATER;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_demo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SimpleAdapter simpleAdapter = new SimpleAdapter();
        mRecycler.setAdapter(simpleAdapter);
        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            objects.add(i + "");
        }
        simpleAdapter.setNewData(objects);

        getOrder();
    }


    public void getOrder() {
        //流水号
        String flowingWater = getFlowingWater();
        //亦或
        String xorHex = HexTool.getInstance().getXOR("3BB304A1" + flowingWater + "0000");
        String order = "3BB304A1" + flowingWater + "0000" + xorHex;
        System.out.print(order);
    }

    public static String getFlowingWater() {
        if (FLOWING_WATER >= 255) {
            FLOWING_WATER = 1;
        } else {
            FLOWING_WATER++;
        }
        return ByteUtil.decimal2fitHex(FLOWING_WATER, 2);
    }
}
