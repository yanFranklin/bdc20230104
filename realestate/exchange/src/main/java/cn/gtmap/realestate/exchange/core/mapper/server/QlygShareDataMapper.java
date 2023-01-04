package cn.gtmap.realestate.exchange.core.mapper.server;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 获取业务办件过程数据
 */
@Component
public interface QlygShareDataMapper {

    /**
     * 获取权利人信息
     *
     * @param map
     * @return
     */
    List<Map> queryQlrxxList(Map map);
}
