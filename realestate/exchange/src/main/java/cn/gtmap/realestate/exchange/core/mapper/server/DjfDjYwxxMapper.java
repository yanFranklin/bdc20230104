package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjYwxxOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记业务信息
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjfDjYwxxMapper {
    /**
     * 获取登记业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjYwxxDO> queryDjfDjYwxxList(Map map);

    /**
     * 获取登记业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjYwxxOldDO> queryDjfDjYwxxListOld(Map map);
}
