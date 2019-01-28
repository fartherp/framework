/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void testDateRedMonthBegin() throws Exception {
        Date date = DateUtil.parse(DateUtil.yyyyMM, "201603");
        String startDate = DateUtil.dateRedMonthBegin(date, 0) + " 00:00:00";
        System.out.println(startDate);
    }

    @Test
    public void testDateRedMonthEnd() throws Exception {
        Date date = DateUtil.parse(DateUtil.yyyyMM, "201603");
        String endDate = DateUtil.dateRedMonthEnd(date, 0) + " 23:59:59";
        System.out.println(endDate);
    }

    @Test
    public void testFormat() throws Exception {
        int count = 2;
        int j = 2;
        while (j > 0) {
            Thread[] threads = new Thread[count];
            for (int i = 0; i < count; i++) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        String date =  DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm_ss, new Date());
                        System.out.println("Thread1 id=[" + Thread.currentThread().getName() + "], date=[" + date + "]");

                        date =  DateUtil.format(DateUtil.yyyy_MM_dd, new Date());
                        System.out.println("Thread1 id=[" + Thread.currentThread().getName() + "], date=[" + date + "]");
                    }
                });
                threads[i] = thread;
            }
            for (int i = 0; i < count; i++) {
                threads[i].start();
            }

            j--;
        }
    }

    @Test
    public void getDateByCalendar() {
        ZoneId zone = ZoneId.systemDefault();
        int ttl = 30;
        Date date = new Date();
        Date date1 = DateUtil.getDateByCalendar(date, Calendar.MINUTE, ttl);
        System.out.println(date1.getTime());
        LocalDateTime localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), zone);
        LocalDateTime localTime1 = localTime.plusMinutes(ttl);
        System.out.println(localTime1.atZone(zone).toInstant().toEpochMilli());

        System.out.println(localTime.plusSeconds(10000 / 1000).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}