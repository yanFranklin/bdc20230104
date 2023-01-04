package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlJsydsyqDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlJsydsyqOldDO;

import java.util.List;
import java.util.Map;

/**
 * 建设用地、宅基地使用权信息
 * Created by zdd on 2015/11/21.
 */
public interface QlfQlJsydsyqMapper {

    /**
     * 获取建设用地、宅基地使用权信息
     *
     * @param map
     * @return
     */
    List<QlfQlJsydsyqDO> queryQlfQlJsydsyqList(Map map);

    /**
     * 获取建设用地、宅基地使用权信息
     *
     * @param map
     * @return
     */
    List<QlfQlJsydsyqOldDO> queryQlfQlJsydsyqListOld(Map map);
}
