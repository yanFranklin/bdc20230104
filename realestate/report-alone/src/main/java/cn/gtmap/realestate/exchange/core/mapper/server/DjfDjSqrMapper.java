package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.DjfDjSqrDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记申请人属性业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjfDjSqrMapper {
    /**
     * 获取登记申请人属性业务信息
     *
     * @param map
     * @return
     */
    List<DjfDjSqrDO> queryDjfDjSqrList(Map map);

}
