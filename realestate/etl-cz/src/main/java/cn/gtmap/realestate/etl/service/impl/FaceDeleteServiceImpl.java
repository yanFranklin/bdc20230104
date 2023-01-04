package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.BdcDsfRlsbDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaceDeleteServiceImpl {
    private final Logger log = LoggerFactory.getLogger(FaceDeleteServiceImpl.class);

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;

    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void deleteQlr(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            log.info("删除qlr信息入参:{}", xmid);
            Example deleteQlrExample = new Example(BdcQlrDO.class);
            deleteQlrExample.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", "1");
            bdcEntityMapper.deleteByExampleNotNull(deleteQlrExample);
            log.info("删除bdcDsfRlsb信息入参:{}", xmid);
            Example deleteBdcDsfRlsbExample = new Example(BdcDsfRlsbDO.class);
            deleteBdcDsfRlsbExample.createCriteria().andEqualTo("ywnum", xmid);
            bdcEntityMapper.deleteByExampleNotNull(deleteBdcDsfRlsbExample);
        } else {
            throw new AppException("xmid为空！deleteQlr请检查参数！");
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void deleteRlsbQlr(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            log.info("删除bdcDsfRlsb信息入参:{}", xmid);
            Example deleteBdcDsfRlsbExample = new Example(BdcDsfRlsbDO.class);
            deleteBdcDsfRlsbExample.createCriteria().andEqualTo("ywnum", xmid);
            bdcEntityMapper.deleteByExampleNotNull(deleteBdcDsfRlsbExample);
        } else {
            throw new AppException("xmid为空！deleteRlsbQlr请检查参数！");
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void deleteBdcQlr(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            log.info("删除qlr信息入参:{}", xmid);
            Example deleteQlrExample = new Example(BdcQlrDO.class);
            deleteQlrExample.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", "1");
            bdcEntityMapper.deleteByExampleNotNull(deleteQlrExample);
        } else {
            throw new AppException("xmid为空！deleteBdcQlr请检查参数！");
        }
    }
}
