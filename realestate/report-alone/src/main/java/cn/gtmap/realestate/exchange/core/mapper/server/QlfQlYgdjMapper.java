package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlYgdjDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 预告登记信息
 * Created by lst on 2015/11/25
 */
@Component
public interface QlfQlYgdjMapper {

    /**
     * 获取预告登记信息
     *
     * @param map
     * @return
     */
    List<QlfQlYgdjDO> queryQlfQlYgdjList(Map map);

}
