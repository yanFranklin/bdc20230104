package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KttFwHDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwHOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 户业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface KttFwHMapper {
    /**
     * 获取户业务信息
     *
     * @param map
     * @return
     */
    List<KttFwHDO> queryKttFwHList(Map map);

    /**
     * 获取户业务信息 用于以人查房
     *
     * @param map
     * @return
     */
    List<KttFwHOldDO> queryKttFwHListForYrcf(Map map);

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 项目内多幢 取fw_xmxx
     */
    List<KttFwHDO> queryKttFwHDzList(Map map);

    /**
     * 获取户业务信息ByBdcdyhList
     *
     * @param map
     * @return
     */
    List<KttFwHDO> queryKttFwHListByBdcdyhList(Map map);

    /**
     * 获取户业务信息
     *
     * @param map
     * @return
     */
    List<KttFwHOldDO> queryKttFwHListOld(Map map);
}
