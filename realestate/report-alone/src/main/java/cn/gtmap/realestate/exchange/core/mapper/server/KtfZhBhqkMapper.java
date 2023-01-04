package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.KtfZhbhqkDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liuyu@gtmap.cn">刘宇</a>
 * @version 1.0, 2017/02/13
 * @description 宗海变化情况Mapper
 */
@Component
public interface KtfZhBhqkMapper {
    /**
     * 宗海变化情况
     *
     * @param map
     * @return
     */
    List<KtfZhbhqkDO> queryKtfZhBhqkList(Map map);

}
