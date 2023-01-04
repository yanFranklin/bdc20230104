package cn.gtmap.realestate.exchange.util;
/*
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/17
 * @description 常用时间处理
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final DateTimeFormatter DATE_FORMATYMD = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA);
    private static final DateTimeFormatter DATE_FORMATYMD_SPLIT = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
    private static final DateTimeFormatter FORMATYMD = DateTimeFormatter.ofPattern("yyMMdd", Locale.CHINA);
    private static final DateTimeFormatter DATE_FORMATYMDHMS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.CHINA);

    private static final DateTimeFormatter DATE_FORMATMD_SPLIT = DateTimeFormatter.ofPattern("MM-dd", Locale.CHINA);

    private static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当前日期 字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DATE_FORMATHMS);
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当前日期 Date
     */
    public static Date getCurDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime2Date(localDateTime);
    }


    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 格式化日期 返回yyyy-MM-dd HH:mm:ss 格式
     */
    public static String formateTime(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATHMS);
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
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 格式化日期 返回yyyyMMdd 格式
     */
    public static String formateTimeYmd(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMD);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 格式化日期 返回yyyy-MM-dd 格式
     */
    public static String formateTimeYmdWithSplit(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMD_SPLIT);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 格式化日期 返回MM-dd 格式
     */
    public static String formateDateMdWithSplit(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATMD_SPLIT);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 格式化日期 返回yy-MM-dd 格式
     */
    public static String formateYmd(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(FORMATYMD);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 将String转date格式的日期
     */
    public static Date getformateTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
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
     * @param localDateTime 日期
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description LocalDateTime 转换为Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }

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
     * 格式化时间
     *
     * @param format
     * @return
     */
    public static String formatDate(String format) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(ldt);
    }
}

