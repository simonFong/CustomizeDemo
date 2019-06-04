package com.simonfong.app2;

import com.simonfong.app2.demo.ByteUtil;
import com.simonfong.app2.demo.HexTool;

import org.junit.Test;

/**
 * Created  on  2019/06/04.
 * interface by
 *
 * @author fengzimin
 */
public class Demo {

    private static int FLOWING_WATER;
    @Test
    public void getNum(){
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
