package cn.gtmap.realestate.exchange.core.mapper.server;


import cn.gtmap.realestate.exchange.core.domain.exchange.QlfFwFdcqQfsyqDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/27
 * @description 建筑物区分所有权业主共有部分信息
 */
public interface QlfFwFdcqQfsyqMapper {
    /**
     * @param map
     * @return
     * @description 获取建筑物区分所有权业主共有部分信息信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<QlfFwFdcqQfsyqDO> queryQlfFwFdcqQfsyqList(Map map);

}
