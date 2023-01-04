package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.ZdKDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 宗地空间属性业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface ZdKMapper {
    /**
     * 获取宗地空间属性业务信息
     *
     * @param map
     * @return
     */
    List<ZdKDO> queryZdKList(Map map);
}
