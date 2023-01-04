package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlGzdjDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 更正登记
 * Created by lst on 2015/12/3
 */
@Component
public interface QlfQlGzdjMapper {

    /**
     * 获取更正登记信息
     *
     * @param map
     * @return
     */
    List<QlfQlGzdjDO> queryQlfQlGzdjList(Map map);
}
