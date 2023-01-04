package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.JyqDkLcfDO;
import cn.gtmap.realestate.common.core.domain.building.JyqDkQlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-6
 * @description 经营权地块权利人服务
 */
public interface JyqDkQlrService {

    /**
     * @param jyqDkDcbIndex 调查表主键
     * @return 经营权权利人
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据调查表主键查询经营权权利人
     */
    List<JyqDkQlrDO> listJyqDkQlrByDjdcbIndex(String jyqDkDcbIndex);

    /**
     * @param jyqdklcfIndex 经营权流出方主键
     * @return 经营权流出方
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据流出方主键查询经营权流出方
     */
    JyqDkLcfDO queryJyqDkLcfByJyqDkLcfIndex(String jyqdklcfIndex);

}
