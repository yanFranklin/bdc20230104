package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
public interface ZdZjdxxService {
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bdcdyh查询宅基地信息
     */
    List<ZdZjdxxDO> listZjdxxByBdcdy(String bdcdyh);
}
