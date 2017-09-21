/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.github.fartherp.framework.common.constant.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The date tools
 * <code>DatUtil</code>
 *
 * @author CK
 */
public class DateUtil {
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String ddMMyy = "ddMMyy";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyMMddHHmmss = "yyMMddHHmmss";
    public static final String yyyyMMddHH = "yyyyMMddHH";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HHmmss = "HHmmss";
    public static final String HH_mm = "HH:mm";

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMdd);
    public static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(yyyy_MM_dd);
    public static final SimpleDateFormat dateFormat3 = new SimpleDateFormat(ddMMyy);
    public static final SimpleDateFormat dateFormat4 = new SimpleDateFormat(yyyyMM);
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
    public static final SimpleDateFormat dateTimeFormat2 = new SimpleDateFormat(yyMMddHHmmss);
    public static final SimpleDateFormat dateTimeFormat3 = new SimpleDateFormat(yyyyMMddHH);
    public static final SimpleDateFormat dateTimeFormat4 = new SimpleDateFormat(yyyyMMddHHmmss);
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(HH_mm_ss);
    public static final SimpleDateFormat timeFormat2 = new SimpleDateFormat(HHmmss);
    public static final SimpleDateFormat timeFormat3 = new SimpleDateFormat(HH_mm);

    /**
     * second
     */
    public static final long SECOND_TIME = 1000;

    /**
     * minute
     */
    public static final long MINUTE_TIME = SECOND_TIME * 60;

    /**
     * hour
     */
    public static final long HOUR_TIME = MINUTE_TIME * 60;

    /**
     * day
     */
    public static final long DAY_TIME = HOUR_TIME * 24;

    /**
     * The format {@link Date} to {@code String}.
     *
     * @param format the specify format
     * @param date   the date {@link Date}
     * @return the new {@code String}
     */
    public static String format(String format, Date date) {
        return DateEnum.getFormatByString(format).format(date);
    }

    /**
     * The parse {@code String} to {@link Date}.
     *
     * @param format the specify format
     * @param date   the date {@code String}
     * @return the new {@link Date}
     */
    public static Date parse(String format, String date) {
        Date d = null;
        try {
            d = DateEnum.getFormatByString(format).parse(date);
        } catch (ParseException e) {
            // ignore
        }
        return d;
    }

    /**
     * Get the {@code String} of the specify format through the specify date {@link Date}
     * and the specify format {@code String} and the specify date type {@code int} and the specify number {@code int}
     *
     * @param date   the date {@link Date}
     * @param format user-defined date format like "yyyy-MM-dd HH:mm:ss"
     * @param i      the specify date type {@link Calendar}
     * @param j      the specify number
     * @return the date {@code String}
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#WEEK_OF_YEAR
     * @see Calendar#DATE
     * @see Calendar#HOUR
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     */
    public static String getDateStr(Date date, String format, int i, int j) {
        Date newDate = getDateByCalendar(date, i, j);
        return DateEnum.getFormatByString(format).format(newDate);
    }

    /**
     * whether leap year
     *
     * @param year the year
     * @return boolean
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
    }

    /**
     * Return the max day of a mouth.
     *
     * @param year  the year
     * @param month the mouth
     * @return the max day of the mouth
     */
    public static int maxDayOfMonth(int year, int month) {
        if (month <= 0 || month > 12) {
            return -1;
        }
        int day = 0;
        switch (month) {
            case 2:
                day = 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            default:
                day = 31;
        }
        if (month == 2 && isLeapYear(year)) {
            day++;
        }
        return day;
    }

    /**
     * Get current the year.
     *
     * @param date the date {@link Date}
     * @return the year {@code int}
     */
    public static int year(Date date) {
        return getCurrentCalendar(date).get(Calendar.YEAR);
    }

    /**
     * Get the current mouth.
     *
     * @param date the date {@link Date}
     * @return the mouth {@code int}
     */
    public static int month(Date date) {
        return getCurrentCalendar(date).get(Calendar.MONTH) + 1;
    }

    /**
     * Get the current day.
     *
     * @param date the date {@link Date}
     * @return the day {@code int}
     */
    public static int day(Date date) {
        return getCurrentCalendar(date).get(Calendar.DATE);
    }

    /**
     * Get the number of the specify date type.
     *
     * @param date the date {@link Date}
     * @param i    the specify date type {@link Calendar}
     * @return the number of the specify date type
     */
    public static int getTimeNumber(Date date, int i) {
        return getCurrentCalendar(date).get(i);
    }

    /**
     * Get the number of today in a week
     *
     * @param date the date {@link Date}
     * @return int
     */
    public static int week(Date date) {
        int week = getCurrentCalendar(date).get(Calendar.DAY_OF_WEEK) - 1;
        if (0 == week) {
            week = 7;
        }
        return week;
    }

    /**
     * Get the current quarter of a year
     *
     * @param date the date {@link Date}
     * @return int
     */
    public static int getQuarter(Date date) {
        return (month(date) - 1) / 3 + 1;
    }

    /**
     * Get the number of days in the current quarter of a year
     *
     * @param date the date {@link Date}
     * @return int
     */
    public static int dayOfQuarter(Date date) {
        int month = month(date);
        int year = year(date);
        int day = 0;
        for (int i = month - (month - 1) % 3; i < month; i++) {
            day += maxDayOfMonth(year, i);
        }
        day += day(date);
        return day;
    }

    /**
     * 返回日期自公元0年开始以来的天数
     *
     * @param date the date {@link Date}
     * @return int
     */
    public static int days(Date date) {
        int days = 0;
        int year = year(date) - 1;
        days += year * 365;// one year have 365 day
        days += year / 4;// 4 year one leap year
        days -= year / 100;// 100 year no leap year
        days += year / 400;// 400 year one leap year
        days += dayOfYear(date);
        return days;
    }

    /**
     * Get the number of today in a year
     *
     * @param date the date {@link Date}
     * @return int
     */
    public static int dayOfYear(Date date) {
        int month = month(date);
        int year = year(date);
        int day = 0;
        for (int i = 1; i < month; i++) {
            day += maxDayOfMonth(year, i);
        }
        day += day(date);
        return day;
    }

    /**
     * Get the {@code String} first day of a mouth
     * through the date {@link Date} and the specify number.
     *
     * @param date the date {@link Date}
     * @param n    the specify number
     * @return the new {@code String}
     */
    public static String dateRedMonthBegin(Date date, int n) {
        String str = "";
        int year = year(date);
        int month = month(date);
        if (n >= 12) {
            int m = n % 12;
            int s = n / 12;
            month = 12 - m + month;
            year = year - s;
        } else {
            month = month - n;
        }
        if (month == 0) {
            year = year - 1;
            month = 12;
        }
        if (month < 10) {
            str = "0" + month;
        } else {
            str = month + "";
        }
        return year + "-" + str + "-01";
    }

    /**
     * Get the {@code String} last day of a mouth
     * through the date {@link Date} and the specify number.
     *
     * @param date the date {@link Date}
     * @param n    the specify number
     * @return int
     */
    public static String dateRedMonthEnd(Date date, int n) {
        String str = "";
        int year = year(date);
        int month = month(date);
        if (n >= 12) {
            int m = n % 12;
            int s = n / 12;
            month = 12 - m + month;
            year = year - s;
        } else {
            month = month - n;
        }
        if (month == 0) {
            year = year - 1;
            month = 12;
        }
        if (month < 10) {
            str = "0" + month;
        } else {
            str = month + "";
        }
        return year + "-" + str + "-" + maxDayOfMonth(year, month);
    }

    /**
     * Get the new {@link Date}
     * through the specify date {@link Date} and the specify date type {@code int}
     * and the specify number {@code int}
     *
     * @param date the date {@link Date}
     * @param i    the specify date type {@link Calendar}
     * @param j    the specify number
     * @return the new {@link Date}
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#WEEK_OF_YEAR
     * @see Calendar#DATE
     * @see Calendar#HOUR
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     */
    public static Date getDateByCalendar(Date date, int i, int j) {
        Calendar c = getCurrentCalendar(date);
        c.add(i, j);
        return c.getTime();
    }

    /**
     * {@link Date} convert {@link Calendar}
     *
     * @param date the date {@link Date}
     * @return {@link Calendar}
     */
    private static Calendar getCurrentCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * Calculate two date diff
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param time      the time diff
     * @return the {@code long}
     * @see #SECOND_TIME
     * @see #MINUTE_TIME
     * @see #HOUR_TIME
     * @see #DAY_TIME
     */
    public static long getBetweenDiff(Date startDate, Date endDate, long time) {
        long startDateTime = startDate.getTime();
        long endDateTime = endDate.getTime();
        return (endDateTime - startDateTime) / time;
    }

    /**
     * validate date {@link Date} whether database default date ("1970-01-01 00:00:00)
     *
     * @param date the date {@link Date}
     * @return boolean
     */
    public static boolean isDBDefaultDate(Date date) {
        if (null == date) {
            return false;
        }
        String dbDefault = format(yyyy_MM_dd_HH_mm_ss, date);
        return Constant.DB_DEFAULT_DATE.equals(dbDefault);
    }

    /**
     * Date Enum
     */
    public static enum DateEnum {
        yyyyMMdd(DateUtil.yyyyMMdd, DateUtil.dateFormat),
        yyyy_MM_dd(DateUtil.yyyy_MM_dd, DateUtil.dateFormat2),
        ddMMyy(DateUtil.ddMMyy, DateUtil.dateFormat3),
        yyyyMM(DateUtil.yyyyMM, DateUtil.dateFormat4),
        yyyy_MM_dd_HH_mm_ss(DateUtil.yyyy_MM_dd_HH_mm_ss, DateUtil.dateTimeFormat),
        yyMMddHHmmss(DateUtil.yyMMddHHmmss, DateUtil.dateTimeFormat2),
        yyyyMMddHH(DateUtil.yyyyMMddHH, DateUtil.dateTimeFormat3),
        yyyyMMddHHmmss(DateUtil.yyyyMMddHHmmss, DateUtil.dateTimeFormat4),
        HH_mm_ss(DateUtil.HH_mm_ss, DateUtil.timeFormat),
        HHmmss(DateUtil.HHmmss, DateUtil.timeFormat2),
        HH_mm(DateUtil.HH_mm, DateUtil.timeFormat3);

        private String dateEnum;

        private SimpleDateFormat simpleDateFormat;

        private DateEnum(String dateEnum, SimpleDateFormat simpleDateFormat) {
            this.dateEnum = dateEnum;
            this.simpleDateFormat = simpleDateFormat;
        }

        /**
         * Get the {@link SimpleDateFormat} through the specify date {@link Enum}
         *
         * @param format the specify date enum
         * @return the {@code SimpleDateFormat}
         */
        public static SimpleDateFormat getFormatByDateEnum(DateEnum format) {
            for (DateEnum dateEnum : DateEnum.values()) {
                if (dateEnum.getDateEnum().equals(format.dateEnum)) {
                    return dateEnum.simpleDateFormat;
                }
            }
            return DateEnum.yyyy_MM_dd_HH_mm_ss.simpleDateFormat;
        }

        /**
         * Get the {@link SimpleDateFormat} through the specify format.
         *
         * @param format the specify format
         * @return the {@code SimpleDateFormat}
         */
        public static SimpleDateFormat getFormatByString(String format) {
            for (DateEnum dateEnum : DateEnum.values()) {
                if (dateEnum.getDateEnum().equals(format)) {
                    return dateEnum.simpleDateFormat;
                }
            }
            return DateEnum.yyyy_MM_dd_HH_mm_ss.simpleDateFormat;
        }

        public String getDateEnum() {
            return dateEnum;
        }

        public SimpleDateFormat getSimpleDateFormat() {
            return simpleDateFormat;
        }
    }
}
