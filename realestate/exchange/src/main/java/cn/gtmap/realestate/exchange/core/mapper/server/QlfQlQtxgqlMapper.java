package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlQtxgqlDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlQtxgqlOldDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/27
 * @description 其他相关权利
 */
public interface QlfQlQtxgqlMapper {
    /**
     * @param map
     * @return
     * @description 获取其他相关权利信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfQlQtxgqlDO> queryQlfQlQtxgqlList(Map map);

    /**
     * @param map
     * @return
     * @description 获取其他相关权利信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfQlQtxgqlOldDO> queryQlfQlQtxgqlListOld(Map map);
}
