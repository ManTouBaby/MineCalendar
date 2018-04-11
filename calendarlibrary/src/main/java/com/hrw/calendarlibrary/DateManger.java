package com.hrw.calendarlibrary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @auther:Herw
 * @describtion: 获取日期之类
 * @date：2016/12/12
 */

public class DateManger {
    /**
     * 获得某年某月的天数
     *
     * @param year  年份
     * @param month 月份
     */
    public static int getMonthDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取该天是星期几
     *
     * @param year
     * @param month
     * @param day
     * @return 一周的第一天为0，以此类推
     */
    public static int getDayOfWeek(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        Date date = string2Date(year, month, day);
        c.setTime(date);
        int num = c.get(Calendar.DAY_OF_WEEK) - 1;
        return num;
    }


    /**
     * 获取当天日期
     *
     * @return 如现在为12号则返回12
     */
    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月份
     *
     * @return 如现在为12月则返回12
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前年份
     *
     * @return 如现在为2016年则返回2016年
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 将Date转换为yyyy-MM-dd
     *
     * @param date
     * @param dateFormat 日期格式"yyyy-MM-dd"
     * @return
     */
    public static String dateFormat(Date date, String dateFormat) {
        SimpleDateFormat dateFm = new SimpleDateFormat(dateFormat);
        String time = dateFm.format(date);
        return time;
    }

    /**
     * 将年月日转换成Date格式
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date string2Date(int year, int month, int day) {
        String datetime = year + "-" + month + "-" + day;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将long转换为yyyy-MM-dd
     *
     * @param time
     * @param dateFormat 日期格式"yyyy-MM-dd"
     * @return
     */
    public static String dateFormat(long time, String dateFormat) {
        Date date = new Date(time);
        SimpleDateFormat dateFm = new SimpleDateFormat(dateFormat);
        String result = dateFm.format(date);
        return result;
    }

}
