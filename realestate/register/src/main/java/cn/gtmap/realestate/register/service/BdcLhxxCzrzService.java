package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/5
 * @description 不动产量化信息操作日志接口
 */
public interface BdcLhxxCzrzService {

    /**
     * 新增量化信息操作日志
     * @param bdcLhxxCzrzDO 量化信息操作日志DO
     */
    void addLhxxCzrz(BdcLhxxCzrzDO bdcLhxxCzrzDO);

    /**
     * 批量新增量化信息操作日志
     * @param bdcLhxxCzrzDOList 量化信息操作日志DO集合
     */
    void plAddLhxxCzrz(List<BdcLhxxCzrzDO> bdcLhxxCzrzDOList);

    /**
     * 查询量化信息操作日志
     * @param bdcLhxxCzrzDO 不动产量化信息操作日志DO
     * @return 量化操作日志集合
     */
    List<BdcLhxxCzrzDO> queryLhxxCzrz(BdcLhxxCzrzDO bdcLhxxCzrzDO);
}
