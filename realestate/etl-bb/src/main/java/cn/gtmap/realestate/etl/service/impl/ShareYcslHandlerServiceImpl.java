package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.TsswDataQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.etl.core.domian.ycsl.*;
import cn.gtmap.realestate.etl.service.ShareYcslHandlerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class ShareYcslHandlerServiceImpl implements ShareYcslHandlerService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareYcslHandlerServiceImpl.class);
    @Autowired
    BdcSwFeignService bdcSwFeignService;
    @Resource(name = "dozerMapper")
    private DozerBeanMapper dozerMapper;
    @Resource(name = "entityMapper")
    private EntityMapper entityMapper;
    @Override
    public void shareYcslDataModel(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        TsswDataQO tsswDataQO = new TsswDataQO();
        tsswDataQO.setGzlslid(processInsId);
        TsswDataDTO tsswDataDTO = bdcSwFeignService.getTsswDataDTO(tsswDataQO);
        if (tsswDataDTO != null) {
            YcslXmDo ycslXmDo = new YcslXmDo();
            YcslBdcdyxxDo ycslBdcdyxxDo = new YcslBdcdyxxDo();
            YcslFwxxDo ycslFwxxDo = new YcslFwxxDo();
            YcslJyxxDo ycslJyxxDo = new YcslJyxxDo();
            dozerMapper.map(ycslXmDo, tsswDataDTO);
            dozerMapper.map(ycslJyxxDo, tsswDataDTO);
            if (tsswDataDTO.getBdcSlXm() != null) {
                dozerMapper.map(ycslXmDo, tsswDataDTO.getBdcSlXm());
                dozerMapper.map(ycslJyxxDo, tsswDataDTO.getBdcSlXm());
            }
            if (tsswDataDTO.getBdcSlJbxx() != null) {
                dozerMapper.map(ycslXmDo, tsswDataDTO.getBdcSlJbxx());
                dozerMapper.map(ycslBdcdyxxDo, tsswDataDTO.getBdcSlJbxx());
            }
            if (tsswDataDTO.getBdcFdcqDO() != null) {
                dozerMapper.map(ycslBdcdyxxDo, tsswDataDTO.getBdcFdcqDO());
                dozerMapper.map(ycslFwxxDo, tsswDataDTO.getBdcFdcqDO());
            }
            if (tsswDataDTO.getBdcSlFwxx() != null) {
                dozerMapper.map(ycslBdcdyxxDo, tsswDataDTO.getBdcSlFwxx());
                dozerMapper.map(ycslFwxxDo, tsswDataDTO.getBdcSlFwxx());
            }
            if (tsswDataDTO.getBdcSlJyxx() != null) {
                dozerMapper.map(ycslJyxxDo, tsswDataDTO.getBdcSlJyxx());
            }
            if (StringUtils.isNotBlank(ycslBdcdyxxDo.getBdcdyxxid())) {
                ycslFwxxDo.setBdcdyxxid(ycslBdcdyxxDo.getBdcdyxxid());
                entityMapper.saveOrUpdate(ycslBdcdyxxDo, ycslBdcdyxxDo.getBdcdyxxid());
            }
            if (StringUtils.isNotBlank(ycslXmDo.getProid())) {
                entityMapper.saveOrUpdate(ycslXmDo, ycslXmDo.getProid());
            }
            if (StringUtils.isNotBlank(ycslFwxxDo.getFwxxid())) {
                entityMapper.saveOrUpdate(ycslFwxxDo, ycslFwxxDo.getFwxxid());
            }
            if (StringUtils.isNotBlank(ycslJyxxDo.getJyxxid())) {
                entityMapper.saveOrUpdate(ycslJyxxDo, ycslJyxxDo.getJyxxid());
            }
            if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrList())) {
                for (BdcSlSqrDO bdcSlSqrDO : tsswDataDTO.getSqrList()) {
                    YcslGxrDo ycslGxrDo = new YcslGxrDo();
                    dozerMapper.map(ycslGxrDo, bdcSlSqrDO);
                    if (tsswDataDTO.getBdcSlJbxx() != null) {
                        dozerMapper.map(ycslGxrDo, tsswDataDTO.getBdcSlJbxx());
                    }
                    if (StringUtils.isNotBlank(ycslGxrDo.getGxrid())) {
                        entityMapper.saveOrUpdate(ycslGxrDo, ycslGxrDo.getGxrid());
                    }
                }
            }
        }
    }
}
