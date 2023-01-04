package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSzDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjSzOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记缮证业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjfDjSzMapper {
    /**
     * 获取登记缮证业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjSzDO> queryDjfDjSzList(Map map);

    /**
     * 获取登记缮证业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjSzOldDO> queryDjfDjSzListOld(Map map);
}
