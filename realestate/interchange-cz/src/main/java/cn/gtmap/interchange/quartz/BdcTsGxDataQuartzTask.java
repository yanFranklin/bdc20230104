package cn.gtmap.interchange.quartz;

import cn.gtmap.interchange.core.domain.InfApply;
import cn.gtmap.interchange.service.InfApplyService;
import cn.gtmap.interchange.service.PushQlygDataService;
import cn.gtmap.interchange.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021-10-13
 * @description 常州定时获取共享数据，推送共享数据至一张网数据库
 */
@Component
@EnableScheduling
public class BdcTsGxDataQuartzTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcTsGxDataQuartzTask.class);

    /**
     * 是否开启推送共享数据定时任务开关
     */
    @Value("${quartz.tsgxsj.enable:false}")
    private boolean tsgxsjEnable;

    @Autowired
    InfApplyService infApplyService;

    @Autowired
    PushQlygDataService pushQlygDataService;


    /**
     * 推送共享数据定时任务
     */
    @Scheduled(cron = "${quartz.tsgxsj.cron:0 0 22 15 * ?}")
    public void BdcTsJkQuartzTask(){
        if(!tsgxsjEnable){
            return;
        }
        LOGGER.info("执行定时推送共享数据任务。");

        // 获取当天未同步、同步失败的的共享数据
        Map<String, Object> param = new HashMap<>();
        param.put("update_date",new Date());
        List<InfApply> infApplyList = this.infApplyService.queryWtbInfApply(param);
        LOGGER.info("获取时间：{} 的共享数据：{} 条", DateUtils.getCurrStrDate(), infApplyList.size());

        if(CollectionUtils.isNotEmpty(infApplyList)){
           this.pushQlygDataService.pushInfApplyData(infApplyList);
        }
    }
}
