package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
import cn.gtmap.realestate.engine.core.service.BdcGzFileService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/7 18:37
 * @description 规则导出文件实现类
 */
@Service
public class BdcGzFileServiceImpl implements BdcGzFileService {

    @Autowired
    private BdcGzZhGzService bdcGzZhGzService;

    @Autowired
    BdcGzZgzService bdcGzZgzService;

    /**
     * 组织组合规则数据返回
     *
     * @param zhid
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public BdcGzZhgzDTO queryZhgzDTO(String zhid) {

        BdcGzZhgzDTO bdcGzZhgzDTO = new BdcGzZhgzDTO();
        List<BdcGzZgzDTO> zgzDTOList = new ArrayList<>();
        BdcGzZhgzDO bdcGzZhgzDO = new BdcGzZhgzDO();
        List<BdcGzZhgzDO> bdcGzZhgzDOList = new ArrayList<>();

        if (StringUtils.isNotBlank(zhid)) {
            bdcGzZhgzDO.setZhid(zhid);
            bdcGzZhgzDOList = bdcGzZhGzService.queryBdcGzZhGzList(bdcGzZhgzDO);
            if(CollectionUtils.isEmpty(bdcGzZhgzDOList)){
                return null;
            }
            bdcGzZhgzDO = bdcGzZhgzDOList.get(0);
            bdcGzZhgzDTO.setZhid(bdcGzZhgzDO.getZhid());
            bdcGzZhgzDTO.setZhmc(bdcGzZhgzDO.getZhmc());
            bdcGzZhgzDTO.setZhbs(bdcGzZhgzDO.getZhbs());
            bdcGzZhgzDTO.setZhsm(bdcGzZhgzDO.getZhsm());
        }

        zgzDTOList = bdcGzZgzService.queryBdcGzZgzDTOListByZhid(zhid);
        bdcGzZhgzDTO.setBdcGzZgzDTOList(zgzDTOList);

        return bdcGzZhgzDTO;
    }
}
