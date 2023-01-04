package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.DjfDjDb;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 登簿信息
 * Created by lst on 2015/12/3
 */
@Component
public interface DjfDjDbxxMapper {

    /**
     * 获取更正登记信息
     *
     * @param map
     * @return
     */
    List<DjfDjDb> queryDjfDbxxList(Map map);
}
