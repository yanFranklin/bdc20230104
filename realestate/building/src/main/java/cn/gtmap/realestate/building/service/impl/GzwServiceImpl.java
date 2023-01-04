package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.GzwDcbService;
import cn.gtmap.realestate.building.core.service.GzwQlrService;
import cn.gtmap.realestate.building.service.GzwService;
import cn.gtmap.realestate.common.core.domain.building.GzwDcbDO;
import cn.gtmap.realestate.common.core.domain.building.GzwQlrDO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物相关服务
 */
@Service
public class GzwServiceImpl implements GzwService{

    @Autowired
    private GzwDcbService gzwDcbService;

    @Autowired
    private GzwQlrService gzwQlrService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询构筑物基本信息（包含权利人）
     */
    @Override
    public GzwDcbResponseDTO queryGzwxxByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){

            // 查询 GZW_DCB 基本信息
            GzwDcbDO gzwDcbDO = gzwDcbService.getGzwDcbDOByBdcdyh(bdcdyh);
            if(gzwDcbDO != null){
                GzwDcbResponseDTO responseDTO = new GzwDcbResponseDTO();
                responseDTO.setGzwDcbDO(gzwDcbDO);

                // 查询 GZW_QLR 列表
                List<GzwQlrDO> gzwQlrDOList = gzwQlrService.listGzwQlrByDcbIndex(gzwDcbDO.getGzwDcbIndex());
                if(CollectionUtils.isNotEmpty(gzwQlrDOList)){
                    responseDTO.setGzwQlrDOList(gzwQlrDOList);
                }
                return responseDTO;
            }
        }
        return null;
    }
}
