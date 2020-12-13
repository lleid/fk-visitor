package com.fk.visitor.api.utils;

import com.google.common.collect.Lists;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

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

    public static Date getMonthFirstate(Date date) {
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

    public static Date getMonthEndDate(Date date) {
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

    public static Date getWeekDFirstate(Date date) {
        LocalDate inputDate = LocalDate.now();
        LocalDate beginDayOfWeek = inputDate.with(DayOfWeek.MONDAY);

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = beginDayOfWeek.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date getWeekEndDate(Date date) {
        LocalDate inputDate = LocalDate.now();
        LocalDate beginDayOfWeek = inputDate.with(DayOfWeek.SUNDAY);

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = beginDayOfWeek.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }


    public static void main(String[] args) {
        Date currentDate = DateUtils.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        Date from = calendar.getTime();

        List<Integer> list = Lists.newArrayList();
        int i = 0;
        while (i < 12) {
            calendar.add(Calendar.MONTH, 1);
            Date to = calendar.getTime();
            System.out.println(from);
            System.out.println(to);
            System.out.println("---------------");

            i = i + 1;
            from = to;
        }

    }
}
