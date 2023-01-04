package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.FwHstDO;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-23
 * @description 户室图相关查询
 */
public interface FwHstMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHstDO>
     * @description 根据户室主键查询户室图
     */
    FwHstDO queryFwHstByFwHsIndex(String fwHsIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param index
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description 根据主键查询户室图
     */
    FwHstDO queryFwHstByIndex(String index);
}
