package cn.gtmap.realestate.exchange.core.mapper.server;


import cn.gtmap.realestate.common.core.domain.exchange.FjFDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 非结构化文档信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface FjFMapper {
    /**
     * 获取非结构化文档信息
     *
     * @param map
     * @return
     */
    List<FjFDO> queryFjFList(Map map);
}
