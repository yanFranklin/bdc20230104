package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzxDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGyJzxOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 界址线信息
 * Created by zdd on 2015/11/21.
 */
@Component
public interface KttGyJzxMapper {

    /**
     * zdd 界址线信息
     *
     * @param map
     * @return
     */
    List<KttGyJzxDO> queryKttGyJzxList(Map map);

    /**
     * zdd 界址线信息
     *
     * @param map
     * @return
     */
    List<KttGyJzxOldDO> queryKttGyJzxListOld(Map map);
}
