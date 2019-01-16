package cn.dlc.customizedemo.myapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wuyufeng    on  2018/11/21 0021.
 * interface by
 */

public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat5 = new SimpleDateFormat("HH");

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     * @param startAt 开始时间，例：2018-11-21
     * @param endAt   结束时间，例：2018-12-20
     * @return 返回日期数组
     */
    public static List<String> queryData(String startAt, String endAt) {
        List<String> dates = new ArrayList<>();
        try {
            Date startDate = dateFormat.parse(startAt);
            Date endDate = dateFormat.parse(endAt);
            dates.addAll(queryData(startDate, endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     * @param startAt 开始时间，例：2018-11-21
     * @param endAt   结束时间，例：2018-12-20
     * @return 返回日期数组
     */
    public static List<String> queryData(Date startAt, Date endAt) {
        List<String> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startAt);
        Calendar end = Calendar.getInstance();
        end.setTime(endAt);
        while (start.before(end) || start.equals(end)) {
            dates.add(dateFormat.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    /**
     * 包含当前日期1个月之后的日期
     * @return
     */
    public static String getCurrent12MonthAfter(String dateType,String dataTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType, Locale.getDefault( ));
        try {
            Date data = sdf.parse(dataTime);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(data);
            if (Calendar.MONTH - 12 != 0) {
                rightNow.add(Calendar.YEAR, 0);// 年份不变
                rightNow.add(Calendar.MONTH, +1);
                //Date dataNow = rightNow.getTime();
                long dataNow = rightNow.getTimeInMillis() - 24 * 3600 * 1000;
                String nowCurrentTime = sdf.format(dataNow);
                return nowCurrentTime;
            } else {
                rightNow.add(Calendar.YEAR, +1);//  年份加1
                rightNow.add(Calendar.MONTH, 1);
                //Date dataNow = rightNow.getTime();
                long dataNow = rightNow.getTimeInMillis() - 24 * 3600 * 10000;
                String nowCurrentTime = sdf.format(dataNow);
                return nowCurrentTime;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 根据当前日期获得是星期几
     * @param formatTip 例如 周 ，星期
     * @param time 2018-11-21
     * @return
     */
    public static String getWeekFormat(String formatTip,String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += formatTip+"日";
        }
        if (wek == 2) {
            Week += formatTip+"一";
        }
        if (wek == 3) {
            Week +=formatTip+ "二";
        }
        if (wek == 4) {
            Week += formatTip+ "三";
        }
        if (wek == 5) {
            Week += formatTip+"四";
        }
        if (wek == 6) {
            Week += formatTip+"五";
        }
        if (wek == 7) {
            Week += formatTip+"六";
        }
        return Week;
    }

    //字符串转时间戳
    public static String getTime(String timeString){
        String timeStamp = null;
        Date d;
        try{
            d = dateFormat.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    
    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * @param formatTime
     * @param day
     * @return
     */
    public static String getDateAfter(String formatTime,int day)  {
        Date parse = null;
        try {
            parse = dateFormat.parse(formatTime);
            Calendar now =Calendar.getInstance();
            now.setTime(parse);
            now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
            return dateFormat.format(now.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return "";
    }

    /**
     * 返回类似 今日(周几)12:20
     */
    public static String getCustomFormatTime(long time){
        StringBuilder stringBuilder = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        String format = dateFormat.format(currentTimeMillis);
        currentTimeMillis = Long.valueOf(getTime(format));
        if(time>=currentTimeMillis && time<currentTimeMillis+24*3600*1000){
            stringBuilder.append("今日");
        }else if(time>=currentTimeMillis+24*3600*1000 && time<currentTimeMillis+2*24*3600*1000){
            stringBuilder.append("明天");
        }else if(time>=currentTimeMillis+2*24*3600*1000 && time<currentTimeMillis+3*24*3600*1000){
            stringBuilder.append("后天");
        }else {
            stringBuilder.append(dateFormat.format(time));
        }
        stringBuilder.append("(").append(getWeekFormat("周",dateFormat.format(time))).append(")");
        stringBuilder.append(dateFormat2.format(time));
        return stringBuilder.toString();
    }

    //字符串转时间戳(2018-12-12 18:30)
    public static String getTime2(String timeString){
        String timeStamp = null;
        Date d;
        try{
            d = dateFormat3.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    //字符串转时间戳(2018-12-12 18:30:00)
    public static String getTime3(String timeString){
        String timeStamp = null;
        Date d;
        try{
            d = dateFormat4.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 返回类似 今天 明天 后天 2018-12-14等等
     */
    public static String getCustomFormatTime2(long time){
        StringBuilder stringBuilder = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        String format = dateFormat.format(currentTimeMillis);
        currentTimeMillis = Long.valueOf(getTime(format));
        if(time>=currentTimeMillis && time<currentTimeMillis+24*3600*1000){
            stringBuilder.append("今天");
        }else if(currentTimeMillis+24*3600*1000>=currentTimeMillis && time<currentTimeMillis+2*24*3600*1000){
            stringBuilder.append("明天");
        }else if(currentTimeMillis+2*24*3600*1000>=currentTimeMillis && time<currentTimeMillis+3*24*3600*1000){
            stringBuilder.append("后天");
        }else {
            stringBuilder.append(dateFormat.format(time));
        }
        return stringBuilder.toString();
    }


    
    /**
     * @param timeString 2018-12-12 18:30:00
     * @return 返回字符串 18:30
     */
    public static String getHourAndMinute(String timeString){
        String time3 = getTime3(timeString);
        return dateFormat2.format(Long.valueOf(time3));
    }

    /**
     * 是否是夜晚
     * @return
     */
    public static Boolean isNight(){
        long l = System.currentTimeMillis();
        String format = dateFormat5.format(l);
        Integer hour = Integer.valueOf(format);
        if(hour>=18 || hour<=6){
          return true;  
        }
        return false;
    }

}

