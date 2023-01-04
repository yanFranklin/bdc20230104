package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.DjfDjShDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记审核业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjfDjShMapper {
    /**
     * 获取登记审核业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjShDO> queryDjfDjShList(Map map);
}
