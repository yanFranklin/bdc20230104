package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/30
 * @description 房屋户室图相关服务
 */
public interface FwHstService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHstIndex
     * @return java.lang.Integer
     * @description 通过主键删除房屋户室图
     */
    Integer deleteFwHstByFwHstIndex(String fwHstIndex);
    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查看逻辑幢下户室图缺失情况
     */
    List<FwHsDO> listHstDeficiency(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description
     */
    FwHstDO queryFwHstByIndex(String fwHstIndex);
}
