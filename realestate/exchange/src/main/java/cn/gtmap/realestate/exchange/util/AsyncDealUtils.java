package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.bo.accessnewlog.Register;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcJrJyjgDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.dto.ywhjsb.BdcWlxmJrCzrzDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            if (Objects.isNull(bdcXmDO)) {
                //业务号可能是外联项目历史关系的gxid，根据关系id 找项目历史关系再关联项目数据
                bdcXmDO = bdcXmMapper.queryBdcxmByLsgxGxid(xmid);
            }
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

    @Async
    public void saveWlxmJrCzrz(BdcWlxmJrCzrzDTO bdcWlxmJrCzrzDTO) {
        if (StringUtils.isNotBlank(bdcWlxmJrCzrzDTO.getXmid())) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(bdcWlxmJrCzrzDTO.getXmid());
            if (Objects.nonNull(bdcXmDO)) {
                BdcJrCzrzDO bdcJrCzrzDO = new BdcJrCzrzDO();
                bdcJrCzrzDO.setRzid(UUIDGenerator.generate16());
                bdcJrCzrzDO.setGzlslid(bdcXmDO.getGzlslid());
                bdcJrCzrzDO.setXmid(bdcWlxmJrCzrzDTO.getGxid());
                bdcJrCzrzDO.setSlbh(bdcXmDO.getSlbh());
                bdcJrCzrzDO.setLcmc(bdcXmDO.getGzldymc());
                bdcJrCzrzDO.setCzsj(bdcWlxmJrCzrzDTO.getCzsj());
                bdcJrCzrzDO.setCzlx(bdcWlxmJrCzrzDTO.getCzlx());
                bdcJrCzrzDO.setCznr(bdcWlxmJrCzrzDTO.getCznr());
                bdcJrCzrzDO.setBwid(bdcWlxmJrCzrzDTO.getBwid());
                bdcJrCzrzDO.setCzjg(bdcWlxmJrCzrzDTO.getCzjg());
                entityMapper.insertSelective(bdcJrCzrzDO);
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 异步保存权籍数据xsd校验失败结果
     * @date : 2022/11/23 17:43
     */
    @Async
    public void saveQjsjXsdJyjg(String xmid, String bdcdyh, String jcjg, Date jcsj) {
        //防止一个流程验证多次，现根据xmid删除之前的异常数据
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcJrJyjgDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExampleNotNull(example);
            BdcJrJyjgDO bdcJrJyjgDO = new BdcJrJyjgDO();
            bdcJrJyjgDO.setId(UUIDGenerator.generate16());
            bdcJrJyjgDO.setXmid(xmid);
            bdcJrJyjgDO.setBdcdyh(bdcdyh);
            bdcJrJyjgDO.setJyjg(jcjg);
            bdcJrJyjgDO.setJcsj(jcsj);
            entityMapper.insertSelective(bdcJrJyjgDO);
        }
    }
}
