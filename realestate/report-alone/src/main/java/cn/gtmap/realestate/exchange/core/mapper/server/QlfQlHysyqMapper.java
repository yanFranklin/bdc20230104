package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlHysyqDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2016/4/20
 * @description 海域使用权Mapper
 */
public interface QlfQlHysyqMapper {
    /**
     * 获取海域使用权信息
     *
     * @param map
     * @return
     */
    List<QlfQlHysyqDO> queryQlfQlHysyqList(Map map);

}
