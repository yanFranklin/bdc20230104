package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwLjzDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 逻辑幢业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface KttFwLjzMapper {
    /**
     * 获取逻辑幢业务信息
     *
     * @param map
     * @return
     */
    List<KttFwLjzDO> queryKttFwLjzList(Map map);

    /**
     * 获取逻辑幢业务信息ByBdcdyhList
     *
     * @param map
     * @return
     */
    List<KttFwLjzDO> queryKttFwLjzListByBdcdyhList(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 项目内多幢取逻辑幢业务信息
     */
    List<KttFwLjzDO> queryKttFwLjzDzList(Map map);

}
