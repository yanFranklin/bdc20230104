package cn.gtmap.realestate.exchange.core.mapper.server;


import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlTdsyqDO;

import java.util.List;
import java.util.Map;

/**
 * 土地所有权
 * Created by lst on 2015/12/3
 */
public interface QlfQlTdsyqMapper {

    /**
     * 获取土地所有权信息
     *
     * @param map
     * @return
     */
    List<QlfQlTdsyqDO> queryQlfQlTdsyqList(Map map);

}
