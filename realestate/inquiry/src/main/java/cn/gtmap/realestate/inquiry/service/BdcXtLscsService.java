package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
public interface BdcXtLscsService {
    /**
     * 批量添加临时参数数据
     * @param lscsDOList 参数数据
     * @return {int} 新增记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    int addValues(List<BdcXtLscsDO> lscsDOList);

    /**
     * 批量删除临时参数数据
     * @param lscsDOList 参数数据
     * @return {int} 删除记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    int deleteRecords(List<BdcXtLscsDO> lscsDOList);
}
