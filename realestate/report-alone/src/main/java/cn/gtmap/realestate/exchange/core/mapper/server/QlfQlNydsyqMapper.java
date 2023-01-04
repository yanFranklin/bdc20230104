package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlNydsyqDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/26
 * @description 农用地使用权(非林地)
 */
public interface QlfQlNydsyqMapper {

    /**
     * @param map
     * @return
     * @description 获取农用地使用权(非林地)登记信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfQlNydsyqDO> queryQlfQlNydsyqList(Map map);

}
