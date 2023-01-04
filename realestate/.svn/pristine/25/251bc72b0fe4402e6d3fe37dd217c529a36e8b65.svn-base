package cn.gtmap.realestate.etl.quartz;

import cn.gtmap.realestate.etl.service.FcjyDataConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0  2020/02/19
 * @description 定时同步备案数据到数据库
 */
@Component
@EnableScheduling
public class SchedulerTask {

    /**
     * 是否开启定时同步房产备案库：REALTYPRESALE_STORAGE数据
     */
    @Value("${tbspfhtbaxx.enable:false}")
    private Boolean tbspfhtbaxxEnable;
    @Autowired
    FcjyDataConvertService fcjyDataConvertService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);

    /**
     * @description 连云港同步房产备案库商品房备案合同信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/27 19:09
     * @return void
     */
    @Scheduled(cron = "${tbspfhtbaxx.cron:0 0 23 * * ? }")
    public void tbSpfHtbaxx() {

        if (!Boolean.TRUE.equals(tbspfhtbaxxEnable)) {
            LOGGER.info("未开启同步商品房合同备案定时任务");
            return;
        }
        try {
            fcjyDataConvertService.convertFcjyHtbaxxAndImoprtBdcDj();
        } catch (Exception e) {
            LOGGER.error("定时同步商品房合同备案信息异常", e);
        }
    }
}
