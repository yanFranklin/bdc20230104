/*
 * Project:  hydroplat-parent
 * Module:   hydroplat-common
 * File:     DateUtils.java
 * Modifier: yangxin
 * Modified: 2014-06-11 10:38
 *
 * Copyright (c) 2014 Mapjs All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent
 * or the registration of a utility model, design or code.
 */

package cn.gtmap.interchange.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 12-8-25
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final FastDateFormat JDK_TIME_FORMAT = FastDateFormat.getInstance("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    public static final FastDateFormat DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat DATEMIN_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final List<FastDateFormat> CUSTOM_FORMATS = Lists.newArrayList(JDK_TIME_FORMAT);

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final Date ZERO = new Date(0);
    public static final long START_SECOND = 1402400000;

    public static void registerFormat(String format) {
        CUSTOM_FORMATS.add(FastDateFormat.getInstance(format));
    }

    public static void registerFormat(String format, TimeZone timeZone) {
        CUSTOM_FORMATS.add(FastDateFormat.getInstance(format, timeZone));
    }

    public static void registerFormat(String format, TimeZone timeZone, Locale locale) {
        CUSTOM_FORMATS.add(FastDateFormat.getInstance(format, timeZone, locale));
    }

    public static Date parse(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }

        if (value instanceof String) {
            String strVal = ((String) value).trim();
            if (strVal.length() == 0) {
                return null;
            } else if (strVal.indexOf('-') != -1) {
                FastDateFormat format;
                switch (strVal.length()) {
                    case 10:
                        format = DATE_FORMAT;
                        break;
                    case 16:
                        format = DATEMIN_FORMAT;
                        break;
                    case 19:
                        format = strVal.indexOf('T') > -1 ? DateFormatUtils.ISO_DATETIME_FORMAT : DATETIME_FORMAT;
                        break;
                    default:
                        format = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT;
                }
                try {
                    return format.parse(strVal);
                } catch (ParseException ignored) {
                }
            } else {
                try {
                    return new Date(Long.parseLong(strVal));
                } catch (NumberFormatException ignored) {
                }
            }
            for (FastDateFormat fdf : CUSTOM_FORMATS) {
                try {
                    return fdf.parse(strVal);
                } catch (ParseException ignored) {
                }
            }
        }
        throw new IllegalArgumentException("Can not cast to Date, value : " + value);
    }

    public static Date now() {
        return new Date();
    }

    public static long toShortSecond(long timestamp) {
        return timestamp / MILLIS_PER_SECOND - START_SECOND;
    }

    public static long toNormalSecond(long shortSecond) {
        return START_SECOND + shortSecond;
    }

    public static long getCurrentShortSecond() {
        return toShortSecond(System.currentTimeMillis());
    }

    private static Pattern NUMBERS = Pattern.compile("^[0-9]+$");
    private static Pattern DAYS = Pattern.compile("^([0-9]+)d$");
    private static Pattern HOURS = Pattern.compile("^([0-9]+)h$");
    private static Pattern MINUTES = Pattern.compile("^([0-9]+)m$");
    private static Pattern SECONDS = Pattern.compile("^([0-9]+)s$");
    private static Pattern MILLISECONDS = Pattern.compile("^([0-9]+)ms$");

    /**
     * Parse a duration
     *
     * @param duration 3h, 2m, 7s, 500ms
     * @return The number of milliseconds
     */
    public static long parseDuration(String duration) {
        if (duration == null) {
            return MILLIS_PER_DAY * 30;
        }
        if (NUMBERS.matcher(duration).matches()) {
            return Integer.parseInt(duration) * MILLIS_PER_SECOND;
        }
        Matcher matcher = SECONDS.matcher(duration);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * MILLIS_PER_SECOND;
        }
        matcher = MINUTES.matcher(duration);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * MILLIS_PER_MINUTE;
        }
        matcher = HOURS.matcher(duration);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * MILLIS_PER_HOUR;
        }
        matcher = DAYS.matcher(duration);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * MILLIS_PER_DAY;
        }
        matcher = MILLISECONDS.matcher(duration);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Invalid duration pattern : " + duration);
    }

    public static Date getNextDay(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * @param
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 获取当前时间  返回格式"yyyyMMdd"
     */
    public static String getCurTimeYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        GregorianCalendar today = new GregorianCalendar();
        String str = sdf.format(today.getTime());
        return str;
    }

    /**
     * @param
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 获取当前时间  返回格式"yyyyMMdd"
     */
    public static String getCurTimeY_M_D() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar today = new GregorianCalendar();
        String str = sdf.format(today.getTime());
        return str;
    }

    /**
     * @param
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 返回当前年
     */
    public static String getCurrYear() {
        GregorianCalendar today = new GregorianCalendar();
        String curYear = today.get(GregorianCalendar.YEAR) + "";
        return curYear;
    }

    /**
     * @param
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 返回当前月日
     */
    public static String getCurrMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        GregorianCalendar today = new GregorianCalendar();
        String str = sdf.format(today.getTime());
        return str;
    }

    /**
     * @param
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @rerutn
     * @description 返回当前月日
     */
    public static String getformateTime(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (date != null) {
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * @param
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @rerutn
     * @description 返回年月日
     */
    public static String getStandardTime(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * @param
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @rerutn
     * @description 返回当前月日
     */
    public static String getCurrStrDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar today = new GregorianCalendar();
        String str = sdf.format(today.getTime());
        return str;
    }

    /**
     * @param
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @rerutn
     * @description 返回当前月日
     */
    public static Date getCurDateHMS() {
        Date date = null;
        GregorianCalendar today = new GregorianCalendar();
        String str = DATETIME_FORMAT.format(today.getTime());
        try {
            date = DATETIME_FORMAT.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static Date parseStringToDate(DateTimeFormatter dateTimeFormatter, String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date utilDate = Date.from(instant);
        return utilDate;
    }

}
