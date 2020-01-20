package com.fuhuitong.nitri.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    /**
     * 默认时区为北京时间
     */
    public static TimeZone DEFAULT_SERVER_TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");

    public static void main(String[] args) {
        // 输入指定日期
        long timeMillis =System.currentTimeMillis();
        Calendar current = Calendar.getInstance(TimeZone.getDefault());
        current.setTimeInMillis(System.currentTimeMillis());

        Calendar date = Calendar.getInstance(TimeZone.getDefault());
        date.setTimeInMillis(timeMillis);
        long time = date.getTimeInMillis() - current.getTimeInMillis();
        // 天
        int day = Math.round(time / 1000 / 60 / 60 / 24);
        // 时
        int hour = Math.round(time / 1000 / 60 / 60 % 24);
        // 分
        int minute = Math.round(time / 1000 / 60 % 60);
        // 秒
        int second = Math.round(time / 1000 % 60);

        System.out.println(String.format("%s天%s时%s分%s秒", day, hour, minute, second));
    }

    /**
     * 设置年、月、日、时、分、秒，并转换成时间戳
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hours
     *            时
     * @param minutes
     *            钟
     * @param seconds
     *            秒
     */
    public static long getTimeInMillis(int year, int month, int day, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        calendar.set(year, month - 1, day, hours, minutes, seconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        try {
            return format.parse(format.format(calendar.getTime())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
