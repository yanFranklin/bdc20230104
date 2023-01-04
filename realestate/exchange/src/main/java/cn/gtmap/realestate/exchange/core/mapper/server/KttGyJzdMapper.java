package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzdDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGyJzdOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 界址点信息
 * Created by zdd on 2015/11/21.
 */
@Component
public interface KttGyJzdMapper {

    /**
     * zdd 查找界址点信息
     *
     * @param map
     * @return
     */
    List<KttGyJzdDO> queryKttGyJzdList(Map map);

    /**
     * zdd 查找界址点信息
     *
     * @param map
     * @return
     */
    List<KttGyJzdOldDO> queryKttGyJzdListOld(Map map);

}
