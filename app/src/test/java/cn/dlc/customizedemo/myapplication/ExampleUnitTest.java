package cn.dlc.customizedemo.myapplication;

import org.junit.Test;

import java.math.BigInteger;

import cn.dlc.customizedemo.myapplication.SerialPort.ByteUtil;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void send() {
        System.out.print(~-40);
        String s = ByteUtil.decimal2fitHex(~-40 + 1);
        System.out.print("\n" + s);
        String s1 = new BigInteger(s, 16).toString(10);
        System.out.print("\n" + s1);
    }

    @Test
    public void getT() {
        System.out.print("\n" + "d8".getBytes());

        System.out.print("\n" + binary("d8", 16, 2));
        int d8 = Integer.parseInt(binary("d8", 16, 10));
        System.out.print("\n" + d8);
        System.out.print("\n" + binary("d8", 16, 10));
        System.out.print("\n" + binary("d8", 16, 16));

    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(String s, int radix, int radix2) {
        return new BigInteger(s, radix).toString(radix2);// 这里的1代表正数
    }

}