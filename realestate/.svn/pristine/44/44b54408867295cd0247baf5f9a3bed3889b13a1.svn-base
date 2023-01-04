package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.core.domian.*;
import cn.gtmap.realestate.etl.core.service.QueryBdcdjDataService;
import cn.gtmap.realestate.etl.core.service.ShareDataService;
import cn.gtmap.realestate.etl.service.ShareDataHandlerService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class ShareDataHandlerServiceImpl implements ShareDataHandlerService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareDataHandlerServiceImpl.class);

    @Autowired
    QueryBdcdjDataService queryBdcdjDataService;
    @Autowired
    ShareDataService shareDataService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Resource(name = "entityMapper")
    private EntityMapper entityMapper;

    @Override
    public void shareDsjjDataByProcessInsId(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例id为空");
        }
        List<BdcXmDO> bdcXmDOList = queryBdcdjDataService.listBdcXmListByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                shareDsjjDataByXm(bdcXmDO);
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                if (CollectionUtils.isNotEmpty(lsgxDOList)) {
                    for (BdcXmLsgxDO bdcXmLsgxDO : lsgxDOList) {
                        LOGGER.info("开始共享大数据局数据，processInsId:{}，上一手历史关系数据：{}", processInsId, JSON.toJSONString(bdcXmLsgxDO));
                        if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid()) && CommonConstantUtils.ZXYQL_ZX.equals(bdcXmLsgxDO.getZxyql())) {
                            shareDsjjDataByXm(entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getYxmid()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void shareDsjjDataByXm(BdcXmDO bdcXmDO) {
        if (bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
            //查询证书YTT_BDCQZS,用zslx做过滤
            List<YttBdcqzsDo> yttBdcqzsDos = queryBdcdjDataService.queryYttBdcqzs(bdcXmDO.getXmid());
            shareDataService.saveOrUpdateYttBdcqzs(yttBdcqzsDos);
            if (CollectionUtils.isNotEmpty(yttBdcqzsDos)) {
                //证书的话 查询GT_CQXX
                shareDataService.saveOrUpdateGtCqxxDos(queryBdcdjDataService.queryGtCqxxs(bdcXmDO.getXmid()));
            }
            //查询证明GT_BDCQZM,用zslx做过滤
            shareDataService.saveOrUpdateGtBdcqzmDos(queryBdcdjDataService.queryGtBdcqzms(bdcXmDO.getXmid()));
            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                //如果是抵押权的话，判断是否为房屋业务
                if (CommonConstantUtils.BHTZM_FW.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))) {
                    shareDataService.saveOrUpdateGtDyaqFwDos(queryBdcdjDataService.queryGtDyaqFws(bdcXmDO.getXmid()));
                } else if (CommonConstantUtils.DZWTZM_TD.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))) {
                    shareDataService.saveOrUpdateGtDyaqTdDos(queryBdcdjDataService.queryGtDyaqTds(bdcXmDO.getXmid()));
                }
            } else if (CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx())) {
                //如果是查封权利，判断是否为房屋业务
                if (CommonConstantUtils.BHTZM_FW.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))) {
                    shareDataService.saveOrUpdateGtCfFwDos(queryBdcdjDataService.queryGtCfFws(bdcXmDO.getXmid()));
                } else if (CommonConstantUtils.DZWTZM_TD.equals(BdcdyhToolUtils.getDzwTzm(bdcXmDO.getBdcdyh()))) {
                    shareDataService.saveOrUpdateGtCfTdDos(queryBdcdjDataService.queryGtCfTds(bdcXmDO.getXmid()));
                }
            }

        }

    }
}
