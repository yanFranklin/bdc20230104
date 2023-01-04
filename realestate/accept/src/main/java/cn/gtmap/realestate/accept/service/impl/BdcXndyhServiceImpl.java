package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcXndyhService;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/28
 * @description 虚拟不动产单元
 */
@Service
public class BdcXndyhServiceImpl implements BdcXndyhService {
    /**
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @return 虚拟不动产单元列表
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 判断虚拟单元号
     */
    @Override
    public List<BdcSlYwxxDTO> listYzXndyh(List<BdcSlYwxxDTO> bdcSlYwxxDTOList) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcSlYwxxDTOList)) {
            throw new AppException("传输数据存在问题请检查");
        } else {
            for (int i = 0; i < bdcSlYwxxDTOList.size(); i++) {
                BdcSlYwxxDTO bdcSlYwxxDTO = bdcSlYwxxDTOList.get(i);
                if(StringUtils.isNotBlank(bdcSlYwxxDTO.getBdcdyh()) &&BdcdyhToolUtils.checkXnbdcdyh(bdcSlYwxxDTO.getBdcdyh())) {
                    bdcSlYwxxDTOS.add(bdcSlYwxxDTO);
                }
            }
        }
        return bdcSlYwxxDTOS;
    }
}
