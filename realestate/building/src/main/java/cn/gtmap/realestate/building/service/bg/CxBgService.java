package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-27
 * @description
 */
public interface CxBgService {

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行撤销变更操作
     */
    void executeBgRevoke(String bgbh);

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键查询变更记录表
     */
    List<SSjHsbgljbDO> listBgjlbByBgbh(String bgbh);

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行撤销变更操作
     */
    void executeBgRevokeByFwHsIndex(String fwHsIndex);
}
