package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.realestate.common.core.dto.portal.BdcBtxyzDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-10-12
 * @description 验证 必填项配置 条件 服务
 */
public interface BdcCheckValidSqlConditionService {

    /**
     * @param bdcBtxyzDTO
     * @param requiredElements
     * @return BdcBtxyzDTO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证批量查询结果中的必填项字段，主要处理配置了gzlslid的sql
     */
    BdcBtxyzDTO checkPlResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements);

    /**
     * @param bdcBtxyzDTO
     * @param requiredElements
     * @return BdcBtxyzDTO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证查询结果中的必填项字段，主要处理配置了xmid的sql
     */
    BdcBtxyzDTO checkResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements);

    /**
     * @param sql
     * @param columnNameSet
     * @return Set<String>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 sql查询字段名称
     */
    Set<String> getColumnName(String sql, Set<String> columnNameSet);

    /**
     * @param resultList requiredElements
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 查询结果为空的字段
     */
    Set<String> addNullColumn(List<Map<String, Object>> resultList, List<FormElementConfigDTO> requiredElements, Set<String> columnSet);

    /**
     * @param resultList
     * @param requiredElements
     * @param columnSet
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 批量查询结果为空的字段
     */
    Set<String> addNullColumnPl(List<Map<String, Object>> resultList, List<FormElementConfigDTO> requiredElements, Set<String> columnSet);
}
