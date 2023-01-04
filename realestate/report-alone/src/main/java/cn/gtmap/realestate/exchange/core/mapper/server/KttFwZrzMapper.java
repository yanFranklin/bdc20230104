package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwZrzDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 自然撞业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface KttFwZrzMapper {
    /**
     * 获取自然撞登记库业务信息
     *
     * @param map
     * @return
     */
    List<Map> queryKttFwZrzFdcqList(Map map);

    /**
     * 获取自然撞业务信息
     *
     * @param map
     * @return
     */
    List<Map> queryKttFwZrzList(Map map);

    /**
     * 获取自然撞业务信息
     *
     * @param map
     * @return
     */
    List<Map> queryKttFwZrzListOld(Map map);

    /**
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [map]
     * @description 获取自然撞业务信息 用于以人查房接口
     */
    List<Map> queryKttFwZrzListForYrcf(Map map);

    /**
     * 获取自然撞业务信息ByBdcdyhList
     *
     * @param map
     * @return
     */
    List<Map> queryKttFwZrzListByBdcdyhList(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 项目内多幢 取自然幢业务信息
     */
    List<KttFwZrzDO> queryKttFwZrzDzList(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 根据不动产单元号获取逻辑幢相关信息
     */
    List<Map> queryFwLjz(Map map);

    /**
     * @param map lszd和zrzh组合成唯一键
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 查询自然幢相关信息
     */
    List<Map> queryFwZrz(Map map);
}
