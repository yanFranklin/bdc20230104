package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.domian.*;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcdjMapper;
import cn.gtmap.realestate.etl.core.service.ShareDataService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class ShareDataServiceImpl implements ShareDataService {

    public static final String SJLY_GX = "2";
    public static final String SJLY_CR = "1";
    @Autowired(required = false)
    @Qualifier("gxsjEntityMapper")
    private EntityMapper gxsjEntityMapper;
    @Autowired(required = false)
    BdcdjMapper bdcdjMapper;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareDataServiceImpl.class);


    @Override
    public void saveOrUpdateYttBdcqzs(List<YttBdcqzsDo> yttBdcqzsDos) {
        if (CollectionUtils.isNotEmpty(yttBdcqzsDos)) {
            LOGGER.info("开始共享大数据局数据，yttBdcqzsDos");
            for (YttBdcqzsDo yttBdcqzsDo : yttBdcqzsDos) {
                if (StringUtils.isAnyBlank(yttBdcqzsDo.getCertno(), yttBdcqzsDo.getQlr())) {
                    continue;
                }
                Example example = new Example(YttBdcqzsDo.class);
                example.createCriteria().andEqualTo("certno", yttBdcqzsDo.getCertno()).andEqualTo("qlr", yttBdcqzsDo.getQlr());
                List<YttBdcqzsDo> yttBdcqzsDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(yttBdcqzsDoTempList)) {
                    for (YttBdcqzsDo yttBdcqzsDoTemp : yttBdcqzsDoTempList) {
                        yttBdcqzsDo.setId(yttBdcqzsDoTemp.getId());
                        yttBdcqzsDo.setSjly(SJLY_GX);
                        yttBdcqzsDo.setCrsj(yttBdcqzsDoTemp.getCrsj());
                        yttBdcqzsDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(yttBdcqzsDo);
                    }
                } else {
                    yttBdcqzsDo.setCrsj(new Date());
                    yttBdcqzsDo.setGxsj(new Date());
                    yttBdcqzsDo.setSjly(SJLY_CR);
                    Integer id = bdcdjMapper.getDsjjCfFwId();
                    yttBdcqzsDo.setId((new Date()).getTime() + id);
                    gxsjEntityMapper.insert(yttBdcqzsDo);
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtBdcqzmDos(List<GtBdcqzmDo> gtBdcqzmDos) {
        if (CollectionUtils.isNotEmpty(gtBdcqzmDos)) {
            LOGGER.info("开始共享大数据局数据，gtBdcqzmDos");
            for (GtBdcqzmDo gtBdcqzmDo : gtBdcqzmDos) {
                if (StringUtils.isAnyBlank(gtBdcqzmDo.getBdcqzh(), gtBdcqzmDo.getQlr())) {
                    continue;
                }
                Example example = new Example(GtBdcqzmDo.class);
                example.createCriteria().andEqualTo("bdcqzh", gtBdcqzmDo.getBdcqzh()).andEqualTo("qlr", gtBdcqzmDo.getQlr());
                List<GtBdcqzmDo> gtBdcqzmDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtBdcqzmDoTempList)) {
                    for (GtBdcqzmDo gtBdcqzmDoTemp : gtBdcqzmDoTempList) {
                        gtBdcqzmDo.setId(gtBdcqzmDoTemp.getId());
                        gtBdcqzmDo.setCrsj(gtBdcqzmDoTemp.getCrsj());
                        gtBdcqzmDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtBdcqzmDo);
                    }
                } else {
                    gtBdcqzmDo.setCrsj(new Date());
                    gtBdcqzmDo.setGxsj(new Date());
                    Integer id = bdcdjMapper.getDsjjCfFwId();
                    if (id != null) {
                        gtBdcqzmDo.setId(id);
                        gxsjEntityMapper.insert(gtBdcqzmDo);
                    }
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtCqxxDos(List<GtCqxxDo> gtCqxxDos) {
        if (CollectionUtils.isNotEmpty(gtCqxxDos)) {
            LOGGER.info("开始共享大数据局数据，gtCqxxDos");
            for (GtCqxxDo gtCqxxDo : gtCqxxDos) {
                if (StringUtils.isAnyBlank(gtCqxxDo.getBdcqzh(), gtCqxxDo.getQlr())) {
                    continue;
                }
                Example example = new Example(GtCqxxDo.class);
                example.createCriteria().andEqualTo("bdcqzh", gtCqxxDo.getBdcqzh()).andEqualTo("qlr", gtCqxxDo.getQlr());
                List<GtCqxxDo> gtCqxxDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtCqxxDoTempList)) {
                    for (GtCqxxDo gtCqxxDoTemp : gtCqxxDoTempList) {
                        gtCqxxDo.setId(gtCqxxDoTemp.getId());
                        gtCqxxDo.setCrsj(gtCqxxDoTemp.getCrsj());
                        gtCqxxDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtCqxxDo);
                    }
                } else {
                    gtCqxxDo.setCrsj(new Date());
                    gtCqxxDo.setGxsj(new Date());
                    gtCqxxDo.setId(UUIDGenerator.generate16());
                    gxsjEntityMapper.insert(gtCqxxDo);
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtDyaqFwDos(List<GtDyaqFwDo> gtDyaqFwDos) {
        if (CollectionUtils.isNotEmpty(gtDyaqFwDos)) {
            LOGGER.info("开始共享大数据局数据，gtDyaqFwDos");
            for (GtDyaqFwDo gtDyaqFwDo : gtDyaqFwDos) {
                if (StringUtils.isAnyBlank(gtDyaqFwDo.getBdcqzh(), gtDyaqFwDo.getQlr())) {
                    continue;
                }
                Example example = new Example(GtDyaqFwDo.class);
                example.createCriteria().andEqualTo("bdcqzh", gtDyaqFwDo.getBdcqzh()).andEqualTo("qlr", gtDyaqFwDo.getQlr());
                List<GtDyaqFwDo> gtDyaqFwDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtDyaqFwDoTempList)) {
                    for (GtDyaqFwDo gtDyaqFwDoTemp : gtDyaqFwDoTempList) {
                        gtDyaqFwDo.setId(gtDyaqFwDoTemp.getId());
                        gtDyaqFwDo.setCrsj(gtDyaqFwDoTemp.getCrsj());
                        gtDyaqFwDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtDyaqFwDo);
                    }
                } else {
                    gtDyaqFwDo.setCrsj(new Date());
                    gtDyaqFwDo.setGxsj(new Date());
                    gtDyaqFwDo.setId(UUIDGenerator.generate16());
                    gxsjEntityMapper.insert(gtDyaqFwDo);
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtDyaqTdDos(List<GtDyaqTdDo> gtDyaqTdDos) {
        if (CollectionUtils.isNotEmpty(gtDyaqTdDos)) {
            LOGGER.info("开始共享大数据局数据，gtDyaqTdDos");
            for (GtDyaqTdDo gtDyaqTdDo : gtDyaqTdDos) {
                if (StringUtils.isAnyBlank(gtDyaqTdDo.getBdcqzh(), gtDyaqTdDo.getQlr())) {
                    continue;
                }
                Example example = new Example(GtDyaqTdDo.class);
                example.createCriteria().andEqualTo("bdcqzh", gtDyaqTdDo.getBdcqzh()).andEqualTo("qlr", gtDyaqTdDo.getQlr());
                List<GtDyaqTdDo> gtDyaqTdDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtDyaqTdDoTempList)) {
                    for (GtDyaqTdDo gtDyaqTdDoTemp : gtDyaqTdDoTempList) {
                        gtDyaqTdDo.setId(gtDyaqTdDoTemp.getId());
                        gtDyaqTdDo.setCrsj(gtDyaqTdDoTemp.getCrsj());
                        gtDyaqTdDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtDyaqTdDo);
                    }
                } else {
                    gtDyaqTdDo.setCrsj(new Date());
                    gtDyaqTdDo.setGxsj(new Date());
                    gtDyaqTdDo.setId(UUIDGenerator.generate16());
                    gxsjEntityMapper.insert(gtDyaqTdDo);
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtCfFwDos(List<GtCfFwDo> gtCfFwDos) {
        if (CollectionUtils.isNotEmpty(gtCfFwDos)) {
            LOGGER.info("开始共享大数据局数据，gtCfFwDos");
            for (GtCfFwDo gtCfFwDo : gtCfFwDos) {
                if (StringUtils.isBlank(gtCfFwDo.getCfwh())) {
                    LOGGER.info("开始共享大数据局数据，gtCfFwDo查封文号为空，gtCfFwDo:{}", JSON.toJSONString(gtCfFwDo));
                    continue;
                }
                Example example = new Example(GtCfFwDo.class);
                example.createCriteria().andEqualTo("cfwh", gtCfFwDo.getCfwh());
                List<GtCfFwDo> gtCfFwDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtCfFwDoTempList)) {
                    for (GtCfFwDo gtCfFwDoTemp : gtCfFwDoTempList) {
                        gtCfFwDo.setId(gtCfFwDoTemp.getId());
                        gtCfFwDo.setCrsj(gtCfFwDoTemp.getCrsj());
                        gtCfFwDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtCfFwDo);
                    }
                } else {
                    gtCfFwDo.setCrsj(new Date());
                    gtCfFwDo.setGxsj(new Date());
                    Integer id = bdcdjMapper.getDsjjCfFwId();
                    if (id != null) {
                        gtCfFwDo.setId(id);
                        gxsjEntityMapper.insert(gtCfFwDo);
                    }
                }
            }
        }
    }

    @Override
    public void saveOrUpdateGtCfTdDos(List<GtCfTdDo> gtCfTdDos) {
        if (CollectionUtils.isNotEmpty(gtCfTdDos)) {
            LOGGER.info("开始共享大数据局数据，gtCfTdDos");
            for (GtCfTdDo gtCfTdDo : gtCfTdDos) {
                if (StringUtils.isBlank(gtCfTdDo.getCfwh())) {
                    LOGGER.info("开始共享大数据局数据，gtCfFwDo查封文号为空，gtCfFwDo:{}", JSON.toJSONString(gtCfTdDo));
                    continue;
                }

                Example example = new Example(GtCfTdDo.class);
                example.createCriteria().andEqualTo("cfwh", gtCfTdDo.getCfwh());
                List<GtCfTdDo> gtCfTdDoTempList = gxsjEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(gtCfTdDoTempList)) {
                    LOGGER.info("开始共享大数据局数据，1");
                    for (GtCfTdDo gtCfTdDoTemp : gtCfTdDoTempList) {
                        LOGGER.info("开始共享大数据局数据，2");
                        gtCfTdDo.setId(gtCfTdDoTemp.getId());
                        gtCfTdDo.setCrsj(gtCfTdDoTemp.getCrsj());
                        gtCfTdDo.setGxsj(new Date());
                        gxsjEntityMapper.updateByPrimaryKeySelective(gtCfTdDo);
                    }
                } else {
                    LOGGER.info("开始共享大数据局数据，3");
                    gtCfTdDo.setCrsj(new Date());
                    gtCfTdDo.setGxsj(new Date());
                    gtCfTdDo.setId(UUIDGenerator.generate16());
                    gxsjEntityMapper.insert(gtCfTdDo);
                }
            }
        }
    }
}
