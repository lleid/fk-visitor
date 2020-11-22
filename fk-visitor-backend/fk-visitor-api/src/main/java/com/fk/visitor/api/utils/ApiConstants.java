package com.fk.visitor.api.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApiConstants {

    public static String formatDuring(long mss) {
        long p = 1000 * 60 * 60;
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % p) / (1000 * 60);
        return supplement(hours) + ":" + supplement(minutes);
    }

    public static String supplement(long num) {
        String str = String.valueOf(num);
        if (str.length() == 1) {
            return "0" + str;
        }
        return str;
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthDFirstate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getMonthDEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    public static int getPeriod(Date from, Date to) {
        Long times = to.getTime() - from.getTime();
        Long days = times / (1000 * 60 * 60 * 24);
        return days.intValue();
    }

    public static void main(String[] args) {
        System.out.println(supplement(10));
    }
}
