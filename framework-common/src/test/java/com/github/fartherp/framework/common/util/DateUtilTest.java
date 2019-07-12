/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class DateUtilTest {

    @Test
    public void testDateRedMonthBegin() {
        Date date = DateUtil.parse(DateUtil.yyyyMM, "201603");
        String startDate = DateUtil.dateRedMonthBegin(date, 0);
		assertEquals(startDate, "2016-03-01");
    }

    @Test
    public void testDateRedMonthEnd() {
        Date date = DateUtil.parse(DateUtil.yyyyMM, "201603");
        String endDate = DateUtil.dateRedMonthEnd(date, 0);
		assertEquals(endDate, "2016-03-31");
    }

    @Test
    public void testFormat() {
        int count = 2;
        int j = 2;
        while (j > 0) {
            Thread[] threads = new Thread[count];
            for (int i = 0; i < count; i++) {
                Thread thread = new Thread(() -> {
					String date =  DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm_ss, new Date());
					assertNotNull(date);

					date =  DateUtil.format(DateUtil.yyyy_MM_dd, new Date());
					assertNotNull(date);
				});
                threads[i] = thread;
            }
            for (int i = 0; i < count; i++) {
                threads[i].start();
            }

            j--;
        }
    }

//    @Test
//    public void getDateByCalendar() {
//        ZoneId zone = ZoneId.systemDefault();
//        int ttl = 30;
//        Date date = new Date();
//        Date date1 = DateUtil.getDateByCalendar(date, Calendar.MINUTE, ttl);
//        System.out.println(date1.getTime());
//        LocalDateTime localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), zone);
//        LocalDateTime localTime1 = localTime.plusMinutes(ttl);
//        System.out.println(localTime1.atZone(zone).toInstant().toEpochMilli());
//
//        System.out.println(localTime.plusSeconds(10000 / 1000).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//    }
}
