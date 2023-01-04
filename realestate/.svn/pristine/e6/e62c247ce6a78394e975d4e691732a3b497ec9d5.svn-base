package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KttFwLjzDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwLjzOldDO;
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
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.KttFwLjzDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [map]
     * @description 获取逻辑幢业务信息用于以人查房
     */
    List<KttFwLjzOldDO> queryKttFwLjzListForYrcf(Map map);

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

    /**
     * 获取逻辑幢业务信息
     *
     * @param map
     * @return
     */
    List<KttFwLjzOldDO> queryKttFwLjzListOld(Map map);
}
