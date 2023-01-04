package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.etl.core.domian.*;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcdjMapper;
import cn.gtmap.realestate.etl.core.service.QueryBdcdjDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class QueryBdcdjDataServiceImpl implements QueryBdcdjDataService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBdcdjDataServiceImpl.class);

    @Autowired(required = false)
    BdcdjMapper bdcdjMapper;
    @Autowired
    EntityZdConvertUtils entityZdConvertUtils;
    @Autowired(required = false)
    @Qualifier("bdcEntityMapper")
    private EntityMapper entityMapper;

    @Override
    public List<YttBdcqzsDo> queryYttBdcqzs(String xmid) {
        List<YttBdcqzsDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryYttBdcqzs(xmid);
            if (CollectionUtils.isNotEmpty(list)) {
                for (YttBdcqzsDo yttBdcqzsDo : list) {
                    entityZdConvertUtils.convertEntityToMc(yttBdcqzsDo);
                }
            }
        }
        return list;
    }

    @Override
    public List<GtBdcqzmDo> queryGtBdcqzms(String xmid) {
        List<GtBdcqzmDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtBdcqzms(xmid);
            if (CollectionUtils.isNotEmpty(list)) {
                for (GtBdcqzmDo gtBdcqzmDo : list) {
                    entityZdConvertUtils.convertEntityToMc(gtBdcqzmDo);
                }
            }
        }
        return list;
    }

    @Override
    public List<GtCqxxDo> queryGtCqxxs(String xmid) {
        List<GtCqxxDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtCqxxs(xmid);
        }
        return list;
    }

    @Override
    public List<GtDyaqFwDo> queryGtDyaqFws(String xmid) {
        List<GtDyaqFwDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtDyaqFws(xmid);
            if (CollectionUtils.isNotEmpty(list)) {
                for (GtDyaqFwDo gtDyaqFwDo : list) {
                    entityZdConvertUtils.convertEntityToMc(gtDyaqFwDo);
                }
            }
        }
        return list;
    }

    @Override
    public List<GtDyaqTdDo> queryGtDyaqTds(String xmid) {
        List<GtDyaqTdDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtDyaqTds(xmid);
            if (CollectionUtils.isNotEmpty(list)) {
                for (GtDyaqTdDo gtDyaqTdDo : list) {
                    entityZdConvertUtils.convertEntityToMc(gtDyaqTdDo);
                }
            }
        }
        return list;
    }

    @Override
    public List<GtCfFwDo> queryGtCfFws(String xmid) {
        List<GtCfFwDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtCfFws(xmid);
        }
        return list;
    }

    @Override
    public List<GtCfTdDo> queryGtCfTds(String xmid) {
        List<GtCfTdDo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            list = bdcdjMapper.queryGtCfTds(xmid);
        }
        return list;
    }

    @Override
    public List<BdcXmDO> listBdcXmListByGzlslid(String gzlslid) {
        Example example = new Example(BdcXmDO.class);
        example.setOrderByClause("xmid ASC");
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExampleNotNull(example);
    }
}
