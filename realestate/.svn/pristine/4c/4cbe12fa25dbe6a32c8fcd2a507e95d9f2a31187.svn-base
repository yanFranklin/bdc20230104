package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.HfFcjyMapper;
import cn.gtmap.realestate.building.service.HfJyztService;
import cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-23
 * @description 合肥交易状态相关服务
 */
@Service
public class HfJyztServiceImpl implements HfJyztService {


    @Autowired
    private HfFcjyMapper hfFcjyMapper;

    /**
     * @param ysfwbmList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YSFWBM 查询 交易状态
     */
    @Override
    public List<HfJyztResponseDTO> queryJyztByYsfwbm(List<String> ysfwbmList) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ysfwbmList",ysfwbmList);
        return hfFcjyMapper.queryJyztByYsfwbm(paramMap);
    }
}
