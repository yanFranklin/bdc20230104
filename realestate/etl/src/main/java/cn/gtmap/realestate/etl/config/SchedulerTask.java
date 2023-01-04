package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.etl.service.EtlRedisService;
import cn.gtmap.realestate.etl.service.impl.EtlRedisServiceImpl;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.realestate.etl.util.QuartzUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-17
 * @description 定时任务执行未处理的数据
 */
@Component
@EnableScheduling
public class SchedulerTask {
    @Autowired
    private EtlRedisService etlRedisService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);

    private static boolean TASK_END_FLAG = true;

    @Scheduled(cron = "${nationalAccess.cron}")
    public void listDjtDjSlSqQuarz() {
        //将未处理的数据存入到redis
        if(StringUtils.equals(EnvironmentConfig.getEnvironment().getProperty("nationalAccess.switch"),"true")
                && TASK_END_FLAG){
            TASK_END_FLAG = false;
            try {
                etlRedisService.saveToRedis(Constants.DJTDJSLSQ_FLAG_ZERO);
                if(QuartzUtil.isDisposeFalseSwitch()){
                    etlRedisService.saveToRedis(Constants.DJTDJSLSQ_FLAG_TWO);
                }
                //处理数据，并维护djtDJSlSqDO表
                etlRedisService.disposeByRedisList();
            } catch (Exception e){
                LOGGER.error("定时任务处理异常",e);
            } finally {
                TASK_END_FLAG = true;
            }
        }
    }

}
