package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.exchange.KttZdjbxxDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.old.KttZdjbxxOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zdd on 2015/11/21.
 */
@Component
public interface KttZdjbxxMapper {

    /**
     * zdd 查找宗地基本信息
     *
     * @param map
     * @return
     */
    List<KttZdjbxxDO> queryKttZdjbxxList(Map map);

    /**
     * zdd 查找宗地基本信息
     *
     * @param map
     * @return
     */
    List<KttZdjbxxOldDO> queryKttZdjbxxListOld(Map map);

    /**
     * zdd 查找宗地基本信息用于以人查房
     *
     * @param map
     * @return
     */
    List<KttZdjbxxDO> queryKttZdjbxxListForYrcf(Map map);

    /**
     * zdd 查找宗地基本信息用于以人查房
     *
     * @param map
     * @return
     */
    List<KttZdjbxxOldDO> queryKttZdjbxxListForYrcfOld(Map map);

    /**
     * zdd 查找宗地基本信息通过bdcdyhList
     *
     * @param map
     * @return
     */
    List<KttZdjbxxDO> queryKttZdjbxxListByBdcdyhList(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 查找宗地调查表信息（坐标）
     */
    List<Map<String, Object>> queryZdDcbList(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 查找农用地调查表信息（坐标）
     */
    List<Map<String, Object>> queryNydDcbList(Map map);
}
