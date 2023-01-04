package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description
 */
public interface NydQlrMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param map
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @description 查询林权相关权利人
     */
    List<NydQlrDO> listLqNydQlr(Map<String,String> map);
}
