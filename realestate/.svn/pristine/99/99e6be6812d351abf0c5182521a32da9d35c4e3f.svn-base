package cn.gtmap.realestate.exchange.quartz;


import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.inf.yancheng.YthxxTsService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-26
 * @description 盐城一体化查解封 相关定时任务
 */
@Component
@EnableScheduling
@Slf4j
public class YcCjfQuarzService {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    YthxxTsService ythxxTsService;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;


    /**
     * 任务是否开启
     */
    @Value("${quartz.yth.cjf.enable:false}")
    private boolean enable;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 盐城一体化查解封定时补推
     */
    @Scheduled(cron = "${quartz.yth.cjf.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_GSGQ, description = "盐城一体化查解封定时补推", waitTime = 60L, leaseTime = 60L)
    public void cjfPush() {
        if (enable) {
            log.info("执行盐城一体化查解封定时补推,{}", new Date());
            Example example = new Example(BdcCzrzDO.class);
            example.createCriteria()
                    .andEqualTo("czlx", BdcCzrzLxEnum.CZRZ_CZLX_YTHCFTS.key())
                    .andEqualTo("czzt", CommonConstantUtils.SF_F_DM);
            List<BdcCzrzDO> bdcCzrzDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcCzrzDOS)) {
                log.info("执行盐城一体化查解封定时补推,需要补推的任务{}", JSON.toJSONString(bdcCzrzDOS));
                //一次处理50条数据
                List<List<BdcCzrzDO>> partition = Lists.partition(bdcCzrzDOS, 50);
                for (List<BdcCzrzDO> bdcCzrzDOList : partition) {
                    bdcCzrzDOList.parallelStream().forEach(bdcCzrzDO -> {
                        try {
                            log.info("执行盐城一体化查解封定时补推,执行任务{}", JSON.toJSONString(bdcCzrzDO));
                            //判断是查封还是解封
                            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcCzrzDO.getXmid());
                            if (bdcQl instanceof BdcCfDO) {
                                ythxxTsService.ythcfxxts(bdcCzrzDO.getGzlslid(), "");
                            } else {
                                ythxxTsService.ythjfxxts(bdcCzrzDO.getGzlslid(), "");
                            }
                        } catch (Exception e) {
                            log.info("执行盐城一体化查解封定时补推,执行任务{}失败,原因{}",
                                    JSON.toJSONString(bdcCzrzDO), e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                log.info("执行盐城一体化查解封定时补推,未找到需要补推的任务{}", new Date());
            }
        }
    }


}
