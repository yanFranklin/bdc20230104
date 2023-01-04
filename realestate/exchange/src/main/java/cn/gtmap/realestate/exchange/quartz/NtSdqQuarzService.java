package cn.gtmap.realestate.exchange.quartz;


import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.NantongQiService;
import cn.gtmap.realestate.common.core.service.rest.exchange.NantongShuiService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
@Slf4j
public class NtSdqQuarzService {
    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    NantongShuiService nantongShuiService;

    @Autowired
    NantongQiService nantongQiService;

    /**
     * 任务是否开启
     */
    @Value("${quartz.sdq.enable:false}")
    private boolean sdqEnable;

    /**
     * 尝试次数
     */
    @Value("${quartz.sdq.tscs:0}")
    private Integer tscs;



    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  定时一张网验证
     */
    @Scheduled(cron = "${quartz.sdq.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_GSGQ, description = "南通水电气失败推送补退", waitTime = 60L, leaseTime = 60L)
    public void sdqPush(){
        if(sdqEnable) {
            log.info("执行南通水电气失败推送补退,{}",new Date());
            BdcSdqywQO bdcSdqghQo = new BdcSdqywQO();
            bdcSdqghQo.setYwlxList(Arrays.asList(BdcSdqEnum.S.key(),BdcSdqEnum.Q.key()));
            bdcSdqghQo.setTscs(tscs);
            bdcSdqghQo.setBlzt(4);
            List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(bdcSdqghQo);
            if(CollectionUtils.isNotEmpty(sdqghDOList)){
                log.info("执行南通水电气失败推送补退,需要补推的任务{}", JSON.toJSONString(sdqghDOList));
                //一次处理50条数据
                List<List<BdcSdqghDO>> sdqghPartition = Lists.partition(sdqghDOList, 50);
                for (List<BdcSdqghDO> bdcSdqghDOS : sdqghPartition) {
                    bdcSdqghDOS.parallelStream().forEach(bdcSdqghDO -> {
                        if(BdcSdqEnum.S.key().equals(bdcSdqghDO.getYwlx())){
                            try {
                                log.info("执行南通水电气失败推送补退,执行任务{}", JSON.toJSONString(bdcSdqghDO));
                                nantongShuiService.sgh(bdcSdqghDO.getGzlslid());
                            } catch (Exception e) {
                                log.info("执行南通水电气失败推送补退,执行任务{}失败,原因{}",
                                        JSON.toJSONString(bdcSdqghDO),e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        if(BdcSdqEnum.Q.key().equals(bdcSdqghDO.getYwlx())){
                            try {
                                log.info("执行南通水电气失败推送补退,执行任务{}", JSON.toJSONString(bdcSdqghDO));
                                nantongQiService.qgh(bdcSdqghDO.getGzlslid());
                            } catch (Exception e) {
                                log.info("执行南通水电气失败推送补退,执行任务{}失败,原因{}",
                                        JSON.toJSONString(bdcSdqghDO),e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else {
                log.info("执行南通水电气失败推送补退,未找到需要补推的任务{}",new Date());
            }
        }
    }


}
