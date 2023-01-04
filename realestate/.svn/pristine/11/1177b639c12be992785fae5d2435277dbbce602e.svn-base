package cn.gtmap.realestate.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-03
 * @description
 */
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String sdf_China = "yyyy年MM月dd日";
    public static final String sdf = "yyyy-MM-dd";
    public static final String sdf_ymd = "yyyyMMdd";
    public static final String sdf_ymdWithSpilt = "yyyy/MM/dd";
    public static final String sdf_ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static final String sdf_ymdhm = "yyyy年MM月dd日 HH时mm分";
    public static final DateTimeFormatter DATE_FORMATYMDHMSF = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMATYMDHMS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMATYMD = DateTimeFormatter.ofPattern(sdf_China, Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMATYMDHMS_SPLIT = DateTimeFormatter.ofPattern(sdf_ymdhms, Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMAT_3 = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMAT_4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMAT_5 = DateTimeFormatter.ofPattern(sdf_ymdWithSpilt, Locale.CHINA);
    public static final DateTimeFormatter DATE_FORMATYMDHM = DateTimeFormatter.ofPattern(sdf_ymdhm, Locale.CHINA);
    public static final String DATE_NYR = "yyyyMMdd";
    public static final String DATE_NY = "yyyyMM";
    public static final String DATE_N = "yyyy";
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat format1 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");
    /**
     * 给Date类型的日期时间 后面加上 time
     *
     * @param date
     * @return
     */
    public static Date dealDate(Date date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date) + " " + time;
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = null;
        try {
            resultDate = dateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(null, e);
        }
        return resultDate;
    }


    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 格式化日期 返回某格式
     */
    public static String formateTime(Date date,DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 格式化日期 返回yyyyMMddHHmmss 格式
     */
    public static String formateTimeYmdhms(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMDHMS);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 格式化日期 返回yyyy年MM月dd日 格式
     */
    public static String formateYmdZw(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMD);
    }

    /**
     * 格式化日期 返回 yyyy-MM-dd hh:mm:ss 格式
     *
     * @param date 日期
     * @return {String} yyyy-MM-dd hh:mm:ss
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String formateYmdhms(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMDHMS_SPLIT);
    }

    /**
     * 将日期进行格式化（参数类型java.util.Date），格式：yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static Date formatDate(String str) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank(str)) {
                date = new SimpleDateFormat(sdf).parse(str);
            }

        } catch (Exception e) {
            LOGGER.info("日期转换失败：{}",str);
            try {
                date = new SimpleDateFormat(sdf_ymd).parse(str);
            } catch (ParseException e1) {
                LOGGER.info("日期转换失败：{}",str);
                try {
                    date = new SimpleDateFormat(sdf_China).parse(str);
                } catch (ParseException e2) {
                    LOGGER.info("日期转换失败：{}",str);
                    try {
                        date = new SimpleDateFormat(sdf_ymdWithSpilt).parse(str);
                    } catch (ParseException e3) {
                        LOGGER.info("日期转换失败：{}",str);
                        return null;
                    }
                }
            }
        }
        return date;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param str
     * @param pattern 模板
     * @return java.util.Date
     * @description 字符串转换DATE类型
     */
    public static Date formatDate(String str,String pattern){
        Date date = null;
        try {
        if (StringUtils.isNotBlank(str)) {
            date = new SimpleDateFormat(pattern).parse(str);
            }
        } catch (ParseException e) {
            LOGGER.info("", e);
        }
        return date;
    }


    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description Date转换为LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 获取季度
     * @param date 日期
     * @return 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 获取指定日期零时零分零秒时间戳
     * @param date 日期
     * @return
     */
    public static long getDayTimeOfZeroHMS(Date date){
        if(null == date){
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 获取指定日期23点时59分59秒时间戳
     * @param date 日期
     * @return
     */
    public static long getDayTimeOfLastHMS(Date date){
        if (null == date) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime();
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
     * 获取当前时间几年前（后）的日期
     * @param year 年份数
     * @param dateFormat 时间格式化（默认yyyy-MM-dd HH:mm:ss）
     * @return 指定时间格式化后字符串
     */
    public static String getSpecifiedYear(int year, String dateFormat) {
        if(StringUtils.isBlank(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, year);
        return DateFormatUtils.format(calendar.getTime(), dateFormat);
    }

    /**
     *
     * @param date
     * @param dateTimeFormatter
     * @return
     */
    public static String formateDateToString(Date date,DateTimeFormatter dateTimeFormatter) {
        if(Objects.isNull(date) || Objects.isNull(dateTimeFormatter)){
            return "";
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 获取当前时间格式字符串，格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getCurrentTimeStr(){
        LocalDateTime localDateTime = date2LocalDateTime(new Date());
        return localDateTime.format(DATE_FORMAT);
    }

    /**
     * 转换时间字符串格式
     */
    public static String convertTimeStr(String dateStr, DateTimeFormatter dateTimeFormatter){
        try {
            Date date = new SimpleDateFormat(DateUtils.sdf_ymdhms).parse(dateStr);
            return DateUtils.formateTime(date, dateTimeFormatter);
        } catch (ParseException e) {
            LOGGER.error("日期格式转换失败，转换的日期字符串为：{}", dateStr);
        }
        return "";
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrYearMonth() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(DATE_NY);
        return format.format(date);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrYearYear() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(DATE_N);
        return format.format(date);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrDay() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(DATE_NYR);
        return format.format(date);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">caolu</a>
     * @description 根据当前时间，获取上个月1号的0点0时0分
     */
    public static String getLastMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.MONTH,cal.get(Calendar.MONTH)-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return DateFormatUtils.format(cal.getTime(), sdf_ymdhms);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">caolu</a>
     * @description 根据当前时间，获取上个季度第一天的0点0时0分
     */
    public static String getLastSeasonStartTime(Date date) {
        int season = getSeason(date) - 1;
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        StringBuilder startTime = new StringBuilder();
        switch (season) {
            case 0:
                startTime.append(year-1).append("-09-01");
                break;
            case 1:
                startTime.append(year).append("-01-01");
                break;
            case 2:
                startTime.append(year).append("-04-01");
                break;
            case 3:
                startTime.append(year).append("-07-01");
                break;
            default:
                break;
        }
        return startTime.append(" 00:00:00").toString();
    }

    /**
     * @param str
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 日期字符串格式 例如： 1640188800000
     * @date : 2021/12/28 11:07
     */
    public static Date getDateFromStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        long time = Long.parseLong(str);
        return new Date(time);
    }

    /**
     * 获取某年第一天日期开始时刻
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirstDay(int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        Date yearFirstDay = cal.getTime();
        return getStartDate(yearFirstDay);
    }

    /**
     * 获取某年最后一天日期最后时刻
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLastDay(int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        Date yearLastDay = cal.getTime();
        return getFinallyDate(yearLastDay);
    }

    /**
     * 得到指定日期的一天的的最后时刻23:59:59
     *
     * @param date
     * @return
     */
    public static Date getFinallyDate(Date date) {
        String temp = format.format(date);
        temp += " 23:59:59";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到指定日期的一天的开始时刻00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        String temp = format.format(date);
        temp += " 00:00:00";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前日期的前一天
     * @param today 当前日期字符串
     */
    public static String getYesterDay(String today){
        Calendar c = Calendar.getInstance();
        Date startTime = DateUtils.formatDate(today, DateUtils.sdf);
        c.setTime(startTime);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        return DateUtils.formateDateToString(c.getTime(), DateUtils.DATE_FORMAT_2);
    }

    /**
     * 获取当前日期的后一天
     * @param today 当前日期字符串
     */
    public static String getTomorrow(String today){
        Calendar c = Calendar.getInstance();
        Date startTime = DateUtils.formatDate(today, DateUtils.sdf);
        c.setTime(startTime);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
        return DateUtils.formateDateToString(c.getTime(), DateUtils.DATE_FORMAT_2);
    }

    /**
     * 将小时转换为天，例如：36小时为1天12小时，48小时为2天
     *
     * @param time 当前日期字符串
     * @return String
     */
    public static String exchangeHour(Integer time) {
        String result;
        if (time >= 24) {
            int day = time / 24;
            int hour = time % 24;
            if (hour == 0) {
                result = day + "天";
            } else {
                result = day + "天" + hour + "小时";
            }
        } else {
            result = time + "小时";
        }
        return result;
    }

    /**
     *  获取当前时间所在月的开始日期
     *
     * @param date 当前日期
     * @return Date
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     *  获取当前时间所在周的开始日期
     *
     * @param date 当前日期
     * @return Date
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return c.getTime();
    }

    public static Date getCurrentYearStartTime() {
        Calendar c = new GregorianCalendar();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.JANUARY)-1, c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.YEAR));
        return c.getTime();
    }

    /**
     *  获取本年的开始日期
     *
     * @param
     * @return String
     */
    public static Date getFirstDayOfYear() {
        Calendar c = new GregorianCalendar();
        c.setTime(getCurrentYearStartTime());
        c.add(Calendar.YEAR, 0);
        return c.getTime();
    }

    /**
     * 将分钟转换为xx天xx小时xx分钟
     *
     * @param minute 分钟
     * @return String
     */
    public static String exchangeMin(Integer minute) {
        String days = "";
        if (minute < 60) {
            days = minute + "分钟";
        } else if (minute < 1440) {
            days = minute / 60 + "小时" + minute % 60 + "分钟";
        } else {
            int minuteValue = minute % 60;
            int hourValue = (minute / 60) % 24;
            int dayValue = (minute / 60) / 24;
            days = dayValue + "天" + hourValue + "小时"
                    + minuteValue + "分钟";
        }
        return days;
    }

}
