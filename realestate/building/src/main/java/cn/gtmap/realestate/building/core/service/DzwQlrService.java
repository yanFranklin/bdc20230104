package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.DzwQlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description 定着物权利人
 */
public interface DzwQlrService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dzwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.DzwQlrDO>
     * @description 根据定着物主键查询定作物权利人
     */
    List<DzwQlrDO> listDzwQlrByDcbIndex(String dzwDcbIndex);
}
