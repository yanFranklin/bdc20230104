package cn.gtmap.realestate.init.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/10.
 * @description
 */
public class DateUtils {

    public static final DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");


    /**
     * 通过格式转成字符串
     * @param dateTimeFormatter
     * @return
     */
    public static String getCurrentDateStr(DateTimeFormatter dateTimeFormatter){
       return new DateTime().toString(dateTimeFormatter);
    }


    /**
     * 获取当前时间
     * @return
     */
    public static Date getCurrentDate(){
        return new DateTime().toDate();
    }

}
