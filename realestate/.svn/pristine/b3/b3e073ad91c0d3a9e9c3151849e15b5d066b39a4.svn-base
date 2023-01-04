package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.WorkDayVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: realestate
 * @description: 工作日的日期计算工具类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-11-12 10:37
 **/
public class DateUtilForWorkDay {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilForWorkDay.class);

    /**
     * 工作日
     */
    public static final int WORKDAY = 0;
    /**
     * 休息日
     */
    public static final int OFFDAY = 1;
    /**
     * 节假日
     */
    public static final int HOLIDAY = 2;

    /**
     * @param calendar 打印数据传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否法定节假日
     */
    public static boolean isLawHoliday(String calendar, List<String> holidayList) throws Exception {
        DateUtilForWorkDay.isValidDate(calendar);
        return holidayList.contains(calendar);
    }

    /**
     * @param calendar 打印数据传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否为周末
     */
    public static boolean isWeekends(String calendar) throws Exception {
        DateUtilForWorkDay.isValidDate(calendar);
        // 先将字符串类型的日期转换为Calendar类型
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(calendar);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * @param calendar 打印数据传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否节假日后补充上班的周末
     */
    public static boolean isExtraWorkday(String calendar,List<String> workdayList) throws Exception {
        DateUtilForWorkDay.isValidDate(calendar);
        return workdayList.contains(calendar);
    }

    /**
     * @param calendar 打印数据传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否是休息日（包含法定节假日和不需要补班的周末）
     */
    public static boolean isHoliday(String calendar,List<String> holidayList,List<String> workdayList) throws Exception {
        DateUtilForWorkDay.isValidDate(calendar);
        // 首先法定节假日必定是休息日
        if (DateUtilForWorkDay.isLawHoliday(calendar,holidayList)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!DateUtilForWorkDay.isWeekends(calendar)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        return !DateUtilForWorkDay.isExtraWorkday(calendar,workdayList);
    }



    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static  Date getNextWorkDay(Date date, int num , List<String> holidayList,List<String> workdayList) throws Exception {
        Date tomorrow = null;
        int delay = 1;
        while(delay <= num){
            tomorrow = getTomorrow(date);
            //当前日期+1,判断是否是节假日,不是的同时要判断是否是周末,都不是则日期+1
            if(!isHoliday(DateFormatUtils.format(tomorrow, "yyyy-MM-dd"),holidayList,workdayList)){
                delay++;
                date = tomorrow;
            } else {
                date = tomorrow;
            }
        }
        return date;
    }

    public static Date getTomorrow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        date = calendar.getTime();
        return date;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口获取大云的配置工作日和节假日
     * @date : 2021/12/28 14:14
     */
    public static WorkDayVO getCloudWorkDays(List<WorkDay> workDays) {
        WorkDayVO workDayVO = new WorkDayVO();
        List<String> workList = new ArrayList<>(workDays.size());
        List<String> holidayList = new ArrayList<>(workDays.size());
        for (WorkDay workDay : workDays) {
            switch (workDay.getDayType()) {
                case WORKDAY:
                    try {
                        if (DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                            workList.add(workDay.getWorkDay());
                        }
                    } catch (Exception e) {
                        LOGGER.info("workList出错");
                    }
                    break;
                case OFFDAY:
                    try {
                        if (!DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                            holidayList.add(workDay.getWorkDay());
                        }
                    } catch (Exception e) {
                        LOGGER.info("holidayList出错");
                    }
                    break;
                case HOLIDAY:
                    holidayList.add(workDay.getWorkDay());
                    break;
                default:
            }
        }
        workDayVO.setWorkList(workList);
        workDayVO.setHolidayList(holidayList);
        return workDayVO;
    }


}
