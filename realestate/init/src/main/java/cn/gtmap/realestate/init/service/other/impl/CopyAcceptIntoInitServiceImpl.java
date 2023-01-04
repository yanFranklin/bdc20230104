package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.CopyAcceptIntoInitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/7
 * @description
 */
@Service
public class CopyAcceptIntoInitServiceImpl implements CopyAcceptIntoInitService {
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    private BdcQllxService bdcQllxService;

    /**
     * @param bdcSlxxDTO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 将受理信息中的权利信息覆盖到对应的权利表的记录中
     */
    @Override
    public void copyBdcSlQlxx(BdcSlxxDTO bdcSlxxDTO) {
        if(bdcSlxxDTO!=null && bdcSlxxDTO.getBdcSlJbxx() != null && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())){
            for(BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()){
                if(bdcSlXmDTO!=null && bdcSlXmDTO.getBdcSlXm()!=null && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXm().getXmid())){
                    BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcSlXmDTO.getBdcSlXm().getXmid());
                    if(bdcQl != null){
                        acceptDozerMapper.map(bdcSlxxDTO.getBdcSlJbxx(),bdcQl);
                        acceptDozerMapper.map(bdcSlXmDTO.getBdcSlXm(),bdcQl);
                        entityMapper.updateByPrimaryKeySelective(bdcQl);
                    }
                }
            }
        }
    }

    @Override
    public void copyBdcSlXmxx(BdcSlxxDTO bdcSlxxDTO) {
        if (bdcSlxxDTO != null && bdcSlxxDTO.getBdcSlJbxx() != null && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                if (bdcSlXmDTO != null && bdcSlXmDTO.getBdcSlXm() != null && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXm().getXmid())) {
                    String xmId = bdcSlXmDTO.getBdcSlXm().getXmid();
                    BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmId);
                    if(bdcXmDO!=null){
                        acceptDozerMapper.map(bdcSlxxDTO.getBdcSlJbxx(),bdcXmDO);
                        acceptDozerMapper.map(bdcSlXmDTO.getBdcSlXm(),bdcXmDO);
                        entityMapper.saveOrUpdate(bdcXmDO, bdcXmDO.getXmid());
                    }
                }
            }
        }
    }
}
