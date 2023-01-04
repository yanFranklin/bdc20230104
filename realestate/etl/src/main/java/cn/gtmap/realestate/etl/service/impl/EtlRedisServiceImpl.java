package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.etl.EtlDjtDjSlsqDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.etl.service.DjtDJSlsqService;
import cn.gtmap.realestate.etl.service.EtlRedisService;
import cn.gtmap.realestate.etl.service.NationalAccessService;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.realestate.etl.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EtlRedisServiceImpl implements EtlRedisService {
    @Autowired
    private DjtDJSlsqService djtDJSlsqService;
    @Autowired
    private NationalAccessService nationalAccessService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EtlRedisServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 将数据存入redis
     */
    @Override
    public void saveToRedis(String dealflag) {
        List<EtlDjtDjSlsqDO> unsettledDOList = djtDJSlsqService.listUnsettledDjtDJSlSqDO(dealflag);
        if (CollectionUtils.isNotEmpty(unsettledDOList)) {
            LOGGER.info("获取数据库数据存入redis, redisname:{},listsize:{}", Constants.ETL_REDIS_LIST_NAME, unsettledDOList.size());
            LOGGER.info("查询的flag为：{}，获取数据库数据数据为：{}", dealflag, unsettledDOList.toString());
            for (DjtDjSlsqDO djtDJSlSqDO : unsettledDOList) {
                redisUtil.rpush(Constants.ETL_REDIS_LIST_NAME, djtDJSlSqDO.getYwh());
                redisUtil.setnx(djtDJSlSqDO.getYwh(), JSONObject.toJSONString(djtDJSlSqDO));
            }
            LOGGER.info("获取数据库数据存入redis结束, redis长度:{}",redisUtil.getListLength(Constants.ETL_REDIS_LIST_NAME));
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理redis中未处理的数据
     */
    @Override
    public void disposeByRedisList() {
        List<String> ywhList = redisUtil.getlist(Constants.ETL_REDIS_LIST_NAME);
        LOGGER.info("处理redis中未处理的数据,redisname:{},ywhlist:{}",Constants.ETL_REDIS_LIST_NAME,ywhList.size());
        if (CollectionUtils.isNotEmpty(ywhList)) {
            for (int i = 0; i < ywhList.size(); i++) {
                String ywh = redisUtil.lpop(Constants.ETL_REDIS_LIST_NAME);
                if (StringUtils.isNotBlank(ywh) && StringUtils.isNotBlank(redisUtil.get(ywh))) {
                    try {
                        EtlDjtDjSlsqDO djtDjSlsqDO = JSONObject.parseObject(redisUtil.get(ywh), EtlDjtDjSlsqDO.class);
                        if(djtDjSlsqDO != null){
                            if (StringUtils.isBlank(djtDjSlsqDO.getBdcdyh())) {
                                LOGGER.info("定时上报国家失败，失败原因：bdcdyh为空。失败的proid=" + djtDjSlsqDO.getYwh()
                                        + "...bdcdyh:" + djtDjSlsqDO.getBdcdyh());
                                nationalAccessService.updateFlagByYwh(djtDjSlsqDO.getYwh(),null, false);
                            } else {
                                Map map = new HashMap();
                                map.put("ywh", djtDjSlsqDO.getYwh());
                                map.put("bdcdyh", djtDjSlsqDO.getBdcdyh());
                                //处理数据
                                LOGGER.info("获取到REDIS中数据，开始处理，ywh:{},bdcdyh:{}",
                                        djtDjSlsqDO.getYwh(),djtDjSlsqDO.getBdcdyh());
                                nationalAccessService.disposeByYwh(djtDjSlsqDO.getYwh(),djtDjSlsqDO.getBdcdyh());
                            }
                        }
                    } catch (Exception e){
                        LOGGER.info("处理redis中未处理的数据异常", e);
                    } finally {
                        //移除ywh
                        redisUtil.del(ywh);
                    }
                }
            }
        }
    }
}
