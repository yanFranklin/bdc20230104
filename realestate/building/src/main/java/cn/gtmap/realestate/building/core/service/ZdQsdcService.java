package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-19
 * @description 宗地权属调查服务
 */
public interface ZdQsdcService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @param qsdcTable
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @description 根据DJH查询宗地权属调查信息
     */
    ZdQsdcDO queryQsdcByDjh(String djh,String qsdcTable);
}
