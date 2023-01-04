package cn.gtmap.realestate.check.service;

import cn.gtmap.realestate.common.core.domain.check.CheckPlanDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/30.
 * @description
 */

public interface CheckPlanService {
    /**
     * 获取检查计划数据
     * @param params
     * @return
     */
    Map<String,Object> getPlanDatas(Map<String,Object> params);

    /**
     * 保存检查计划
     * @param checkPlanDO
     */
    void saveOrUpdatePlan(CheckPlanDO checkPlanDO);
    /**
     * 查询检查计划
     * @param checkPlanDO
     */
    List<CheckPlanDO> queryCheckPlan(CheckPlanDO checkPlanDO);
}
