package cn.gtmap.realestate.inquiry.service.jsc.impl;

import cn.gtmap.realestate.common.core.enums.SummaryDimension;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.inquiry.util.Constants;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TendencyPadding {
    public static final String YEAR = "1";
    public static final String MONTH = "2";
    public static final String WEEK = "3";
    public static final String DAY = "4";
    public static final String TENYEAR = "5";

    public static final List<String> DEFAULTQX = Arrays.asList("宣州区", "郎溪县", "泾县", "绩溪县", "旌德县", "宁国市", "广德市");

    public static <T> void paddingResultDate(Map<String, List<T>> results, JscCommomQO jscCommomQO) {

        try {
            if (Objects.isNull(jscCommomQO.getStartTime()) || Objects.isNull(jscCommomQO.getEndTime())) {
                return;
            }
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar.setTime(jscCommomQO.getStartTime());
            endCalendar.setTime(jscCommomQO.getEndTime());
            List<String> allDate = new ArrayList<>();
            if (jscCommomQO.getSummaryDimension().toUpperCase().equals(SummaryDimension.MONTH.getCode())) {
                //按照月份补充数据
                while (!startCalendar.after(endCalendar)) {
                    allDate.add(DateUtil.format(startCalendar.getTime(), Constants.DATE_NY));
                    startCalendar.add(Calendar.MONTH, 1);
                }
            } else if (jscCommomQO.getSummaryDimension().toUpperCase().equals(SummaryDimension.DAY.getCode())) {
                //按照天补充数据
                while (!startCalendar.after(endCalendar)) {
                    allDate.add(DateUtil.format(startCalendar.getTime(), Constants.DATE_NYR));
                    startCalendar.add(Calendar.DATE, 1);
                }
            }else if (jscCommomQO.getSummaryDimension().toUpperCase().equals(SummaryDimension.YEAR.getCode())) {
                //按照年补充数据
                while (!startCalendar.after(endCalendar)) {
                    allDate.add(DateUtil.format(startCalendar.getTime(), Constants.DATE_N));
                    startCalendar.add(Calendar.YEAR, 1);
                }
            }

            if (CollectionUtils.isNotEmpty(allDate)) {
                for (String time : allDate) {
                    if (!results.containsKey(time)) {
                        results.put(time, Collections.emptyList());
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public static <T> void paddingResultQx(Map<String, List<T>> results, JscCommomQO jscCommomQO) {
        try {
            for (String defaultqx : DEFAULTQX) {
                if (!results.containsKey(defaultqx)) {
                    results.put(defaultqx, new ArrayList<>());
                }
            }
        } catch (Exception e) {

        }
    }

    public static  void paddingResultQxNum(Map<String, Integer> results, JscCommomQO jscCommomQO) {
        try {
            for (String defaultqx : DEFAULTQX) {
                if (!results.containsKey(defaultqx)) {
                    results.put(defaultqx, 0);
                }
            }
        } catch (Exception e) {

        }
    }


    /**
     * 设置请求时
     *
     * @param jscCommomQO
     */
    public static void paddingQueryTime(JscCommomQO jscCommomQO) {
        if (Objects.nonNull(jscCommomQO.getStartTime()) || Objects.nonNull(jscCommomQO.getEndTime())) {
            return;
        }

        if (Objects.isNull(jscCommomQO.getTimeFrame())) {
            return;
        }
        //近10年
        if (jscCommomQO.getTimeFrame().equals(TENYEAR)) {
            jscCommomQO.setStartTime(
                    DateUtil.beginOfYear(
                    DateUtil.parse("2018-01-01", Constants.DATE_NYR))
                    .toJdkDate()
            );
            jscCommomQO.setEndTime(new Date());
        }
        //近一年
        if (jscCommomQO.getTimeFrame().equals(YEAR)) {
            jscCommomQO.setStartTime(DateUtil.beginOfYear(new Date()).toJdkDate());
            jscCommomQO.setEndTime(new Date());
        }

        //近一个月
        if (jscCommomQO.getTimeFrame().equals(MONTH)) {
            jscCommomQO.setStartTime(DateUtil.beginOfMonth(new Date()).toJdkDate());
            jscCommomQO.setEndTime(new Date());
        }

        //近一周
        if (jscCommomQO.getTimeFrame().equals(WEEK)) {
            jscCommomQO.setStartTime(DateUtil.beginOfWeek(new Date()).toJdkDate());
            jscCommomQO.setEndTime(new Date());
        }

        //今天
        if (jscCommomQO.getTimeFrame().equals(DAY)) {
            jscCommomQO.setStartTime(DateUtil.beginOfDay(new Date()).toJdkDate());
            jscCommomQO.setEndTime(new Date());
        }

    }
}
