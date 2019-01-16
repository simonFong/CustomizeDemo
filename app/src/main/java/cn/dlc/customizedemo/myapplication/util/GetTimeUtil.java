package cn.dlc.customizedemo.myapplication.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fengzimin  on  2018/4/28.
 * interface by
 */
public class GetTimeUtil {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";


    /**
     * 发送时间
     *
     * @param timeStamp
     * @return
     */
    public static String format(long timeStamp) {

        Date date = new Date(timeStamp);

        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    /**
     * @param timeStamp 需要判断的时间戳
     * @return 带格式的时间
     */
    public static String getTime(long timeStamp) {
        String strTime = "";
        Date curDate = new Date(timeStamp);
        SimpleDateFormat formatter;


        //今天
        Calendar todayCal = Calendar.getInstance();
        Date todayTime = todayCal.getTime();

        //昨天
        Calendar yesterdayCal = Calendar.getInstance();
        yesterdayCal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayTime = yesterdayCal.getTime();

        //前天
        Calendar dayCal = Calendar.getInstance();
        dayCal.add(Calendar.DAY_OF_MONTH, -2);
        Date dayTime = dayCal.getTime();

        //上一年
        Calendar yearCal = Calendar.getInstance();
        dayCal.add(Calendar.YEAR, -1);
        Date yearTime = yearCal.getTime();

        if (curDate.getDate() == todayTime.getDate()) {//判断是否今天
            formatter = new SimpleDateFormat("HH:mm");
            strTime = formatter.format(timeStamp);
            return strTime;
        } else if (curDate.getDate() == yesterdayTime.getDate()) {//判断是否昨天
            return "昨天";
        } else if (curDate.getMonth() < todayTime.getMonth() || curDate.getDate() < yesterdayTime
                .getDate()) {//判断是否前天或者前天以前
            formatter = new SimpleDateFormat("MM月dd日");
        } else if (curDate.getYear() != yearTime.getYear()) {//判断是否同一年
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
        } else {
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
        }
        strTime = formatter.format(timeStamp);
        return strTime;
    }
}


