package cn.gtmap.realestate.exchange.core.mapper.server;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/4/27
 * @description 构(建)筑物所有权
 */
public interface QltQlGjzwsyqMapper {

    /**
     * @param map
     * @return
     * @description 获取构(建)筑物所有权信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<Map> queryQltQlGjzwsyqList(Map map);

    /**
     * @param map
     * @return
     * @description 获取构(建)筑物所有权信息
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     */
    List<Map> queryQltQlGjzwsyqListOld(Map map);
}
