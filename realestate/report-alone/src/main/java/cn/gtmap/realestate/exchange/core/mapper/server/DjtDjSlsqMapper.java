package cn.gtmap.realestate.exchange.core.mapper.server;
import cn.gtmap.realestate.exchange.core.domain.exchange.DjtDjSlsqDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登记受理申请业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface DjtDjSlsqMapper {
    /**
     * 获取登记受理申请业务信息
     *
     * @param map
     * @return
     */
    List<DjtDjSlsqDO> queryDjtDjSlsqList(Map map);

    /**
     * 获取登记受理申请业务信息(不关联BDC_SHXX)
     *
     * @param map
     * @return
     */
    List<DjtDjSlsqDO> queryDjtDjSlsqListForTj(Map map);

}
