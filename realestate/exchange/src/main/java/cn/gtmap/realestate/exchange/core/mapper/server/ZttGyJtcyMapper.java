package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.ZttGyJtcyDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 家庭成员信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface ZttGyJtcyMapper {
    /**
     * 获取权利人业务信息
     *
     * @param map
     * @return
     */
    List<ZttGyJtcyDO> queryZttGyJtcyList(Map map);

}
