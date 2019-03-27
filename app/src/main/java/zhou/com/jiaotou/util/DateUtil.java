package zhou.com.jiaotou.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhou on 2017/10/21.
 */

public class DateUtil {

    public static String showTime(String time) throws ParseException {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendTime = sdf.parse(time);
        long result = currentTime.getTime() - sendTime.getTime();//转为秒
        String r = "";
        if(result < 60000){// 一分钟内
            long seconds = result / 1000;
            if(seconds <= 0){
                r = "刚刚";
            }else{
                r = seconds + "秒前";
            }
        }else if (result >= 60000 && result < 3600000){// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        }else if (result >= 3600000 && result < 86400000){// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        }else if (result >= 86400000 && result < 1702967296){// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        }else{// 日期格式
            return "1月前";
        }
        return r;
    }

    //2007.07.04
    public static String dotDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String time = format.format(date);
        return time;
    }

    //2017-12-12
    public static String lineDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    //4th Jul 2017
    public static String blankDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d'th' MMM yyyy");
        String time = format.format(date);
        return time;
    }

    //8th June 12:33
    public static String hourDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d'th' MMM HH:mm");
        String time = format.format(date);
        return time;
    }

    //20/8/2017
    public static String reverseDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String time = format.format(date);
        return time;
    }

    public static String lineHDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        return time;
    }

    //专门给交投请求角标减去一分钟的方法
    public static String jiaotouDate(){
        Calendar cal = Calendar.getInstance();
        String monthString = "";
        String dayString = "";
        String hourString = "";
        String minuteString = "";
        String secondString = "";
        String sumString = "";
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute=cal.get(Calendar.MINUTE)-1;//分
        int second=cal.get(Calendar.SECOND);//秒


        if (month<10){
            monthString = "0"+month;
        }else {
            monthString = ""+month;
        }
        if (day<10){
            dayString = "0"+day;
        }else {
            dayString = ""+day;
        }
        if (hour<10){
            hourString = "0"+hour;
        }else {
            hourString = ""+hour;
        }
        if (minute<0){
            minuteString = "00";
        }
        else if (0<minute && minute<10){
            minuteString = "0"+minute;
        }else {
            minuteString = ""+minute;
        }
        if (second<10){
            secondString = "0"+second;
        }else {
            secondString = ""+second;
        }
        sumString = year+monthString+dayString+hourString+minuteString+secondString;
        return sumString;
    }

}
