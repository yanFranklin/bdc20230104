package cn.gtmap.realestate.inquiry.quartz;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxZzTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.service.BdcDpCxService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/15/23:09
 * @Description:
 */
@Component
@EnableScheduling
public class InquiryQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryQuartzService.class);
    @Autowired
    BdcDpCxService bdcDpCxService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询住宅统计定时任务是否开启
     */
    @Value("${quartz.queryZztj.enable:false}")
    private boolean queryZztjEnable;

    @Value("${quartz.queryJrdjlx.enable:false}")
    private boolean queryJrdjlxEnable;

    @Scheduled(cron = "${quartz.queryZztj.cron:0 0/30 * * * ?}")
    public void BdcZztjQuartzTask(){
        if(queryZztjEnable){
            LOGGER.info("执行查询住宅统计定时任务。");
            BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
            BdcDpCxZzTjDTO zzTj = bdcDpCxService.getZzTj(bdcDpTjQO);
            redisUtils.addStringValue(CommonConstantUtils.REDIS_INQUIRY_ZZTJ, JSON.toJSONString(zzTj), 60*60);
        }
    }

    @Scheduled(cron = "${quartz.queryJrdjlx.cron:0 0/30 * * * ?}")
    public void QueryJrdjlxQuartzTask(){
        if(queryJrdjlxEnable){
            LOGGER.info("执行查询今日登记类型定时任务。");
            BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
            List<BdcDpCxTjDTO> bdcDpCxTjDTOS = bdcDpCxService.listJrdjlx(bdcDpTjQO);
            redisUtils.addStringValue(CommonConstantUtils.REDIS_INQUIRY_JRDJLX, JSON.toJSONString(bdcDpCxTjDTOS), 60*60);
        }
    }


}
