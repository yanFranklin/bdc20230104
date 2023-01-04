package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlCfdjDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 查封登记信息
 * Created by lst on 2015/11/25
 */
@Component
public interface QlfQlCfdjMapper {

    /**
     * 获取预告登记信息
     *
     * @param map
     * @return
     */
    List<QlfQlCfdjDO> queryQlfQlCfdjList(Map map);

}
