package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QltQlLqDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QltQlLqOldDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/20
 * @description 林权Mapper
 */
public interface QltQlLqMapper {
    /**
     * 获取林权信息
     *
     * @param map
     * @return
     */
    List<QltQlLqDO> queryQltQlLqList(Map map);

    /**
     * 获取林权信息
     *
     * @param map
     * @return
     */
    List<QltQlLqOldDO> queryQltQlLqListOld(Map map);
}
