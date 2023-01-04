package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.engine.core.dto.BdcGzBdsTsxxDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/3
 * @description 子规则验证逻辑
 */
public interface BdcGzZgzCheckService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则
     * @return {List} 规则提示信息
     * @description 获取单个子规则校验结果信息
     */
    <T> BdcGzZgzTsxxDTO getZgzCheckResult(BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDOList  子规则集合
     * @return {List} 规则提示信息
     * @description  获取多个子规则校验结果信息
     */
    List<BdcGzZgzTsxxDTO> getZgzCheckResult(List<BdcGzZgzDTO> bdcGzZgzDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @param bdcGzYzQO 验证参数
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     */
    List<BdcGzZgzTsxxDTO> getZgzSjlResult(List<BdcGzZgzDTO> bdcGzZgzDTOList, BdcGzYzQO bdcGzYzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @param paramMapList 验证参数
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     */
    List<BdcGzZgzTsxxDTO> getZgzSjlResult(List<BdcGzZgzDTO> bdcGzZgzDTOList, List<Map<String, Object>> paramMapList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @description 获取子规则验证表达式转换为Drools表达式信息
     */
    List<BdcGzBdsTsxxDTO> getZgzBdsDrools(List<BdcGzZgzDTO> bdcGzZgzDTOList);
}
