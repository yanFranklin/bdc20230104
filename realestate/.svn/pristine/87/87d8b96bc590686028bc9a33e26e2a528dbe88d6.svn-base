package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HZdQlrDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
public interface ZdQlrService {

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询宗地权利人
     */
    List<ZdQlrDO> listZdQlrByBdcdyh(String bdcdyh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @description 根据调查表主键查询权利人
     */
    List<ZdQlrDO> listZdQlrByDjdcbIndex(String djdcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @description 根据调查表主键查询备份权利人
     */
    List<HZdQlrDO> listHZdQlrByDjdcbIndex(String djdcbIndex);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @description 根据DJH查询宗地权利人
     */
    List<ZdQlrDO> listZdQlrByDjh(String djh);

    List<ZdQlrDO> listZdQlrByDjhs(List<Object> djhs);
}
