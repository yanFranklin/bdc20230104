package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HZhQlrDO;
import cn.gtmap.realestate.common.core.domain.building.ZhQlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
 * @description
 */
public interface ZhQlrService {

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bdcdyh查询宗海权利人信息
     */
    List<ZhQlrDO> listZhQlrByBdcdyh(String bdcdyh);

    /**
     * @param zhDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据zhDcbIndex查询宗海权利人信息
     */
    List<ZhQlrDO> listZhQlrByZhDcbIndex(String zhDcbIndex);

    /**
     * @param zhDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据zhDcbIndex查询备份宗海权利人信息
     */
    List<HZhQlrDO> listHZhQlrByZhDcbIndex(String zhDcbIndex);


}
