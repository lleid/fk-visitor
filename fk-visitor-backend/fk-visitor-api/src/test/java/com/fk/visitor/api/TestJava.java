package com.fk.visitor.api;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TestJava {
    public static void main(String[] args) {
        LocalDate inputDate = LocalDate.now();
// 所在周开始时间
        LocalDate beginDayOfWeek = inputDate.with(DayOfWeek.MONDAY);

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = beginDayOfWeek.atStartOfDay().atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
// 所在周结束时间
        LocalDate endDayOfWeek = inputDate.with(DayOfWeek.SUNDAY);

        System.out.println(beginDayOfWeek);
        System.out.println(endDayOfWeek);
    }
}
