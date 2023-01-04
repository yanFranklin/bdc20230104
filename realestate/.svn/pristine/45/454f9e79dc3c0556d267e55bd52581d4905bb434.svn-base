package cn.gtmap.realestate.exchange.quartz.shucheng;


import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.shucheng.ShuChengSqdService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-26
 * @description 一张网 相关定时任务
 */
@Component
@EnableScheduling
public class ShuChengSdqQuarzService {

    private static Logger LOGGER = LoggerFactory.getLogger(ShuChengSdqQuarzService.class);

    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;


    @Autowired
    ShuChengSqdService shuChengSqdService;

    /**
     * 任务是否开启
     */
    @Value("${quartz.shucheng.sdq.enable:false}")
    private boolean sdqEnable;

    /**
     * 推送次数
     */
    @Value("${quartz.shucheng.sdq.tscs:0}")
    private Integer tscs;



    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 补退水过户
     */
    @Scheduled(cron = "${quartz.sdq.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_GSGQ, description = "舒城水失败推送补退", waitTime = 60L, leaseTime = 60L)
    public void sdqPush(){
        if(sdqEnable) {
            LOGGER.info("执行舒城水失败推送补退,{}",new Date());
            BdcSdqywQO bdcSdqghQo = new BdcSdqywQO();
            bdcSdqghQo.setYwlxList(Arrays.asList(BdcSdqEnum.S.key()));
            bdcSdqghQo.setTscs(tscs);
            bdcSdqghQo.setBlzt(4);
            List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(bdcSdqghQo);
            if(CollectionUtils.isNotEmpty(sdqghDOList)){
                LOGGER.info("执行舒城水电气失败推送补退,需要补推的任务{}", JSON.toJSONString(sdqghDOList));
                //一次处理50条数据
                List<List<BdcSdqghDO>> sdqghPartition = Lists.partition(sdqghDOList, 50);
                for (List<BdcSdqghDO> bdcSdqghDOS : sdqghPartition) {
                    bdcSdqghDOS.parallelStream().forEach(bdcSdqghDO -> {
                        if(BdcSdqEnum.S.key().equals(bdcSdqghDO.getYwlx())){
                            try {
                                LOGGER.info("执行舒城水失败推送补退,执行任务{}", JSON.toJSONString(bdcSdqghDO));
                                shuChengSqdService.sgh(bdcSdqghDO.getGzlslid(), bdcSdqghDO.getConsno(),"");
                            } catch (Exception e) {
                                LOGGER.info("执行舒城水失败推送补退,执行任务{}失败,原因{}",
                                        JSON.toJSONString(bdcSdqghDO),e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else {
                LOGGER.info("执行舒城水电气失败推送补退,未找到需要补推的任务{}", new Date());
            }
        }
    }


}
