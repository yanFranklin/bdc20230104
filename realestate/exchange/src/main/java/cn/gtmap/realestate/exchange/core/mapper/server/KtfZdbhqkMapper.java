package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KtfZdbhqkDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KtfZdbhqkOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 宗地变化情况
 * Created by zdd on 2015/11/21.
 */
@Component
public interface KtfZdbhqkMapper {

    /**
     * zdd 查找宗地变化情况
     *
     * @param map
     * @return
     */
    List<KtfZdbhqkDO> queryKtfZdbhqkList(Map map);

    /**
     * zdd 查找宗地变化情况
     *
     * @param map
     * @return
     */
    List<KtfZdbhqkOldDO> queryKtfZdbhqkListOld(Map map);
}
