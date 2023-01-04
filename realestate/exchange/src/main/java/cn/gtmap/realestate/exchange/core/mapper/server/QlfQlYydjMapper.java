package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYydjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYydjOldDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/26
 * @description 异议登记
 */
public interface QlfQlYydjMapper {
    /**
     * @param map
     * @return
     * @description 获取异议信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfQlYydjDO> queryQlfQlYydjList(Map map);

    /**
     * @param map
     * @return
     * @description 获取异议信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfQlYydjOldDO> queryQlfQlYydjListOld(Map map);
}
