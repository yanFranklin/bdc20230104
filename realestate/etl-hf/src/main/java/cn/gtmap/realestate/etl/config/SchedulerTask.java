package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.etl.core.mapper.bdcdj.QuartzMapper;
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
    private QuartzMapper quartzMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);


    @Scheduled(cron = "${dsjj.sszx.cron}")
    public void listDjtDjSlSqQuarz() {
        //将未处理的数据存入到redis
        if (StringUtils.equals(EnvironmentConfig.getEnvironment().getProperty("dsjj.sszx.switch"), "true")) {
            try {
                quartzMapper.updateZsSsxz();
                quartzMapper.updateZmSsxz();
            } catch (Exception e) {
                LOGGER.error("定时任务处理异常", e);
            }
        }
    }

}
