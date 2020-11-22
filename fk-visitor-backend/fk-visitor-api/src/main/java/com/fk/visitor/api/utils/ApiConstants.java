package com.fk.visitor.api.utils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("all")
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

    public static int getPeriod(Date from, Date to) {
        Long times = to.getTime() - from.getTime();
        Long days = times / (1000 * 60 * 60 * 24);
        return days.intValue();
    }

    public static void main(String[] args) {
        System.out.println(supplement(10));
    }
}
