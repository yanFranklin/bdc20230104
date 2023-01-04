package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: realestate
 * @description: 上报销账定时服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-22 11:31
 **/
@Component
@EnableScheduling
public class SbxzQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SbxzQuartzService.class);
    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    /*
     * 是否开启上报销账定时任务*/
    @Value("${sbxz.open:false}")
    private boolean sbxz;


    @Value("${access.sbxz.clsj.open:false}")
    private boolean clsjSbxz;


    @Scheduled(cron = "${access.sbxz.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_SBXZ, description = "上报销账状态更新", waitTime = 10L, leaseTime = 600L)
    public void updateXzzt() {
        if (sbxz) {
            LOGGER.warn("开始更新销账状态定时任务");
            accesssModelHandlerService.updateXzzt();
        }
    }


    @Scheduled(cron = "${access.sbxz.clsj.cron:0 0 21 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_CLSJSBXZ, description = "存量数据定时销账任务", waitTime = 10L, leaseTime = 600L)
    public void clsjSbxz() {
        if (clsjSbxz) {
            LOGGER.warn("开始更新存量数据销账状态定时任务");
            accesssModelHandlerService.updateClsjXzzt();
        }
    }


}
