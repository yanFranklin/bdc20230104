package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcCxsqsMapper;
import cn.gtmap.realestate.inquiry.service.BdcCxsqsSlbhScService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/10/9
 * @description 查询申请书受理编号生成服务
 */
@Service
public class BdcCxsqsSlbhScServiceImpl implements BdcCxsqsSlbhScService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCxsqsServiceImpl.class);

    @Autowired
    BdcCxsqsMapper bdcCxsqsMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisUtils redisUtils;

    /**
     * @return Stirng 查询申请书受理编号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成查询申请书受理登记编号
     */
    @Override
    public BdcCxsqsDO generateCxsqsSldjBh() {
        BdcCxsqsDO bdcCxsqsDO = new BdcCxsqsDO();
        String timeFormat = "yyyyMMdd";
        Date currentDate = new Date();
        String dateStr = DateFormatUtils.format(currentDate, timeFormat);
        RLock rLock = null;
        try {
            /**
             * 1、加锁，60s内尝试获取锁，加锁后30s内若未手动释放锁则自动释放锁
             */
            rLock = redissonClient.getLock(CommonConstantUtils.REDIS_LOCK_CXSQS_SLBH);
            if (rLock.tryLock(60, 30, TimeUnit.SECONDS)) {
                int count = bdcCxsqsMapper.countCxsldjbh(dateStr);
                String yjdBh = dateStr + String.format("%05d", count + 1);
                bdcCxsqsDO.setCxsldjbh(yjdBh);
                bdcCxsqsDO.setSqsid(UUIDGenerator.generate16());
                entityMapper.insertSelective(bdcCxsqsDO);
            }
        } catch (Exception e) {
            LOGGER.error("查询申请书受理登记编号创建异常！", e);
            throw new AppException("查询申请书受理登记编号创建异常！" + CommonConstantUtils.REDIS_LOCK_CXSQS_SLBH);
        } finally {
            // 释放锁
            if (null != rLock) {
                rLock.unlock();
            }
        }
        return bdcCxsqsDO;
    }
}
