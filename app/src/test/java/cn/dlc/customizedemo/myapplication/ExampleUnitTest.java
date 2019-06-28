package cn.dlc.customizedemo.myapplication;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Calendar;

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


    @Test
    public void main() {
        boolean b = judgeIsIn(2, 5, "10:00", "11:30");
        System.out.print("是否在范围里：" + b);
    }

    /**
     * @param startweekday 1
     * @param endweekday   7
     * @param starttime    8:00
     * @param endtime      10:00
     */
    public boolean judgeIsIn(int startweekday, int endweekday, String starttime, String endtime) {
        //0.获取当前时间
        Calendar instance = Calendar.getInstance();
        int weekday = instance.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : instance.get(Calendar.DAY_OF_WEEK) - 1;
//        int weekday = instance.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = instance.get(Calendar.HOUR_OF_DAY);
        int minuteOfDay = instance.get(Calendar.MINUTE);
        //1.判断是否在规定的星期几以内
        if (weekday < startweekday || weekday > endweekday) {
            return false;
        }
        //2.判断是否在规定的小时以内
        String[] startSplit = starttime.split(":");
        int startHour = Integer.parseInt(startSplit[0]);
        int startMinute = Integer.parseInt(startSplit[1]);
        String[] endSplit = endtime.split(":");
        int endHour = Integer.parseInt(endSplit[0]);
        int endMinute = Integer.parseInt(endSplit[1]);
        if (hourOfDay < startHour || hourOfDay > endHour) {
            return false;
        }
        //3.判断是否在规定的分钟内
        if (hourOfDay == startHour && minuteOfDay < startMinute) {
            return false;
        }
        if (hourOfDay == endHour && minuteOfDay > endMinute) {
            return false;
        }
        return true;
    }


}