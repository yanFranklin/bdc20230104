package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjSjOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记收件业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjfDjSjMapper {
    /**
     * 获取登记收件业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjSjDO> queryDjfDjSjList(Map map);

    /**
     * 获取登记收件业务信息with SjId
     *
     * @param map
     * @return
     */
    List<DjfDjSjDO> queryDjfDjSjListWithSjId(Map map);

    /**
     * 获取登记收件业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjSjOldDO> queryDjfDjSjListOld(Map map);
}
