package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/11
 * @description 规则子系统规则参数处理相关逻辑接口定义
 */
public interface BdcGzCsService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList  子规则集合
     * @param paramMap  参数集合
     * @description  设置子规则参数值
     */
    void matchParamValue(List<BdcGzZgzDTO> bdcGzZgzDTOList, Map<String, Object> paramMap);
}
