package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.bo.accessnewlog.Register;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 异步处理工具类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-04 18:53
 **/
@Component
public class AsyncDealUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDealUtils.class);


    @Autowired
    @Qualifier("serverEntityMapper")
    private EntityMapper entityMapper;
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    AccessLogMapper accessLogMapper;


    @Async
    public void plbcDbrzXq(List<Register> getRegister, String dbrzid) {
        if (CollectionUtils.isNotEmpty(getRegister)) {
            List<BdcDbrzjlXqDO> bdcDbrzjlXqDOList = new ArrayList<>();
            for (Register register : getRegister) {
                BdcDbrzjlXqDO bdcDbrzjlXqDO = new BdcDbrzjlXqDO();
                bdcDbrzjlXqDO.setId(UUIDGenerator.generate16());
                bdcDbrzjlXqDO.setDbrzid(dbrzid);
                bdcDbrzjlXqDO.setXmid(register.getYWH());
                bdcDbrzjlXqDO.setBwid(register.getBWID());
                bdcDbrzjlXqDO.setBdcdyh(register.getBDCDYH());
                bdcDbrzjlXqDOList.add(bdcDbrzjlXqDO);
            }
            LOGGER.warn("当前登簿日志记录id{}开始插入登簿日志详情{}条", dbrzid, CollectionUtils.size(bdcDbrzjlXqDOList));
            entityMapper.insertBatchSelective(bdcDbrzjlXqDOList);
        }
    }

    @Async
    public void saveJrCzrz(String xmid, Integer czlx, String cznr, String bwid, Date czsj, String czjg) {
        if (StringUtils.isBlank(xmid) && StringUtils.isNotBlank(bwid)) {
            //根据bwid 查接入表
            BdcJrrzQO bdcJrrzQO = new BdcJrrzQO();
            bdcJrrzQO.setBwid(bwid);
            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrrz(bdcJrrzQO);
            if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                xmid = bdcAccessLogList.get(0).getYwlsh();
            }
        }
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            if (Objects.nonNull(bdcXmDO)) {
                BdcJrCzrzDO bdcJrCzrzDO = new BdcJrCzrzDO();
                bdcJrCzrzDO.setRzid(UUIDGenerator.generate16());
                bdcJrCzrzDO.setGzlslid(bdcXmDO.getGzlslid());
                bdcJrCzrzDO.setXmid(bdcXmDO.getXmid());
                bdcJrCzrzDO.setSlbh(bdcXmDO.getSlbh());
                bdcJrCzrzDO.setLcmc(bdcXmDO.getGzldymc());
                bdcJrCzrzDO.setCzsj(czsj);
                bdcJrCzrzDO.setCzlx(czlx);
                bdcJrCzrzDO.setCznr(cznr);
                bdcJrCzrzDO.setBwid(bwid);
                bdcJrCzrzDO.setCzjg(czjg);
                entityMapper.insertSelective(bdcJrCzrzDO);
            }
        }
    }
}
