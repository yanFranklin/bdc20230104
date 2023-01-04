package cn.gtmap.realestate.etl.util;
/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0, 2019/04/23
 * @description 常用时间处理
 */
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final DateTimeFormatter DATE_FORMATYMD = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA);
    private static final DateTimeFormatter FORMATYMD = DateTimeFormatter.ofPattern("yyMMdd", Locale.CHINA);


    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取当前日期 字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DATE_FORMATHMS);
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取当前日期 Date
     */
    public static Date getCurDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime2Date(localDateTime);
    }


    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 格式化日期 返回yyyy-MM-dd HH:mm:ss 格式
     */
    public static String formateTime(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATHMS);
    }

    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 格式化日期 返回yyyy-MM-dd 格式
     */
    public static String formateTimeYmd(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DATE_FORMATYMD);
    }
    /**
     * @param date 日期
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 格式化日期 返回yy-MM-dd 格式
     */
    public static String formateYmd(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(FORMATYMD);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
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
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
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
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description LocalDateTime 转换为Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

}

