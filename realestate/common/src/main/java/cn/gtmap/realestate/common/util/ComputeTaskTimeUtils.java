package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/124
 * @description 计算任务处理时长
 */

@Component
public class ComputeTaskTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeTaskTimeUtils.class);
    /**
     * 工作日配置ID
     */
    public static final String WORK_DAY_REDIS_KEY = "WORK_DAY_ID";
    /**
     * 时间格式化格式
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s", Locale.CHINA);

    @Autowired
    WorkDayClient workDayClient;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 计算任务处理时长
     * <p>任务时长计算去除节假日
     *      例如： 1号上午接收，1号上午处理完成时长为：0.5; 1号下午完成：1; 2号上午完成为：1.5; 以此类推 </p>
     * @param startTime 任务开始时间
     * @param endTime 任务结束时间
     * @return 任务处理时长 0.5，1，1.5
     */
    public Object computeTaskTime(String startTime, String endTime){
        // 获取时间段内，工作日配置
        Map<String, List<String>> workDayConfigMap = this.getWorkDayList(startTime, endTime);
        // 时间段内，所有的日期
        List<String> allWorkDayStrList = workDayConfigMap.get("allWorkDay");
        // 时间段内，非工作日
        List<String> notWorkDayList = workDayConfigMap.get("notWorkDay");
        if(CollectionUtils.isNotEmpty(allWorkDayStrList) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            String startStr = DateUtils.convertTimeStr(startTime, DateUtils.DATE_FORMAT_2);
            String endStr = DateUtils.convertTimeStr(endTime, DateUtils.DATE_FORMAT_2);
            List<String> doWorkDayList = allWorkDayStrList.stream().skip(allWorkDayStrList.indexOf(startStr))
                    .limit(allWorkDayStrList.indexOf(endStr)-allWorkDayStrList.indexOf(startStr)).collect(Collectors.toList());

            int workDayNum = doWorkDayList.size();
            if(CollectionUtils.isNotEmpty(doWorkDayList) && CollectionUtils.isNotEmpty(notWorkDayList)){
                doWorkDayList.removeAll(notWorkDayList);
                workDayNum = doWorkDayList.size();
            }

            return this.computeSzsc(workDayNum, startTime, endTime);
        }
        return "";
    }

    /**
     * 计算任务处理时长（工作日数据缓存处理模式）
     * <p>针对于大批量计算任务处理时长，知道最开始任务处理时间，已当前时间做为结束时间，获取这段时间范围内的工作日配置。</p>
     *
     * @param kssj 查询任务开始时间（用于获取开始时间至现在的工作日配置）
     * @param startDate 任务开始时间
     * @param endDate 任务结束时间
     * @return 任务处理时长 0.5，1，1.5
     */
    public Object computeTaskTimeWithCache(String kssj, String startDate, String endDate){
        String fmtJssj = DateUtils.formateTime(new Date(), DateUtils.DATE_FORMAT_2);
        Map<String, List<String>> cacheMap = this.getWorkDayListByRedis(kssj, fmtJssj);
        List<String> allWorkDayStrList = cacheMap.get("allWorkDay");
        List<String> notWorkDayList = cacheMap.get("notWorkDay");

        if(CollectionUtils.isNotEmpty(allWorkDayStrList) && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            String startStr = DateUtils.convertTimeStr(startDate, DateUtils.DATE_FORMAT_2);
            String endStr = DateUtils.convertTimeStr(endDate, DateUtils.DATE_FORMAT_2);
            List<String> doWorkDayList = allWorkDayStrList.stream().skip(allWorkDayStrList.indexOf(startStr))
                    .limit(allWorkDayStrList.indexOf(endStr)-allWorkDayStrList.indexOf(startStr)).collect(Collectors.toList());

            int workDayNum = doWorkDayList.size();
            if(CollectionUtils.isNotEmpty(doWorkDayList) && CollectionUtils.isNotEmpty(notWorkDayList)){
                doWorkDayList.removeAll(notWorkDayList);
                workDayNum = doWorkDayList.size();
            }

            return this.computeSzsc(workDayNum, startDate, endDate);
        }
        return "";
    }

    private Map<String,  List<String>> getWorkDayList(String kssj, String jssj){
        Map<String, List<String>> resultMap = new HashMap<>(2);

        // 获取区间内的 工作日与非工作日
        List<WorkDay> allWorkDayList = workDayClient.getWorkDays(this.getWorksId(), kssj, jssj);

        // 获取非工作日时间
        List<String> notWorkDayList = allWorkDayList.stream().parallel().filter(t-> !Objects.equals(0, t.getDayType()))
                .map(WorkDay::getWorkDay).collect(Collectors.toList());

        // 获取缮证时间区间
        List<String> allWorkDayStrList = allWorkDayList.stream().map(WorkDay::getWorkDay).collect(Collectors.toList());
        resultMap.put("notWorkDay", notWorkDayList);
        resultMap.put("allWorkDay", allWorkDayStrList);
        return resultMap;
    }

    /**
     * 获取缓存中的工作日设置
     */
    private Map<String, List<String>> getWorkDayListByRedis(String kssj, String jssj){
        String key = kssj + "_" +jssj;
        Map<String, String> workDayMap = redisUtils.getHash(key);
        Map<String, List<String>> resultMap = new HashMap<>(2);
        if(MapUtils.isNotEmpty(workDayMap)){
            resultMap.put("notWorkDay", JSON.parseArray(workDayMap.get("notWorkDay"), String.class));
            resultMap.put("allWorkDay", JSON.parseArray(workDayMap.get("allWorkDay"), String.class));
        }else{
            resultMap = this.getWorkDayList(kssj, jssj);
            // 添加到 redis 缓存中
            redisUtils.addHashValue(key, "notWorkDay", JSON.toJSONString(resultMap.get("notWorkDay")), 300);
            redisUtils.addHashValue(key, "allWorkDay", JSON.toJSONString(resultMap.get("allWorkDay")), 300);

        }
        return resultMap;
    }

    /**
     * 获取大云工作日配置worksId
     */
    private String getWorksId(){
        String worksId = redisUtils.getStringValue(WORK_DAY_REDIS_KEY);
        if(StringUtils.isBlank(worksId)){
            List<Work> works = workDayClient.getWorks();
            worksId = works.get(0).getId();
            redisUtils.addStringValue(WORK_DAY_REDIS_KEY, worksId, 7200L);
        }
        return worksId;
    }


    /**
     * 计算缮证时长
     */
    private Object computeSzsc(int workDayNum, String startDate, String endDate){
        // 获取时长类型
        String type = this.getCompareTimeType(startDate, endDate);
        double szsc = 0;
        switch(type){
            case "00":
            case "11":
                szsc = workDayNum + 0.5;
                break;
            case "01":
                szsc = workDayNum + 1;
                break;
            case "10":
                szsc = workDayNum;
                break;
            default:
        }
        return szsc;
    }

    /**
     * 获取前后时间比对类型
     *  小时数小于 12 则为上午（类型为0）， 小时数大于 12 则为下午（类型为1）
     *  例：小时数为上午、下午 类型为：01
     */
    private String getCompareTimeType(String startDateStr, String endDateStr){
        int startHour = this.getHour(startDateStr);
        int endHour = this.getHour(endDateStr);
        String startHourType = startHour > 12 ? "1" : "0";
        String endHourType = endHour > 12 ? "1" : "0";
        return startHourType + endHourType;
    }

    private int getHour(String time){
        LocalDateTime ldt = LocalDateTime.parse(time, DATE_FORMAT);
        LocalTime lt = ldt.toLocalTime();
        return lt.getHour();
    }

}
