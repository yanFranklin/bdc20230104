package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.KtfZhYhydzbDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liuyu@gtmap.cn">刘宇</a>
 * @version 1.0, 2017/02/13
 * @description 用海、用岛坐标
 */
@Component
public interface KtfZhYhYdzbMapper {
    /**
     * 用海、用岛坐标
     *
     * @param map
     * @return
     */
    List<KtfZhYhydzbDO> queryKtfZhYhYdzbList(Map map);
}
