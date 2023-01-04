package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-28
 * @description 户室变更记录表服务
 */
public interface SSjHsbgjlbService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sSjHsbgljbDO
     * @return SSjHsbgljbDO
     * @description 户室变更记录实体
     */
    SSjHsbgljbDO insertHsBgjl(SSjHsbgljbDO sSjHsbgljbDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sSjHsbgljbDOList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @description 批量新增户室变更记录
     */
    List<SSjHsbgljbDO> batchInsertHsBgjl(List<SSjHsbgljbDO> sSjHsbgljbDOList);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @description 根据现房屋户室主键查询记录表
     */
    List<SSjHsbgljbDO> listHsBgjlByFwHsIndex(String fwHsIndex);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param yfwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @description 根据原房屋户室主键查询记录表
     */
    List<SSjHsbgljbDO> listHsBgjlByYFwHsIndex(String yfwHsIndex);
}
