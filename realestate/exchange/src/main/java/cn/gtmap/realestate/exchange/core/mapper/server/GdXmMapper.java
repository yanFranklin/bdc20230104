package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuruijie on 2018/5/17.
 */
public interface GdXmMapper {

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 分页获取获取过渡项目
     */
    List<Map> getGdXmByPage(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡抵押信息
     */
    List<QlfQlDyaqDO> queryQlfQlDyaqNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡户室信息
     */
    List<KttFwHDO> queryKttFwHNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡查封信息
     */
    List<QlfQlCfdjDO> queryQlfQlCfdjNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡建设用地、宅基地使用权信息
     */
    List<QlfQlJsydsyqDO> queryQlfQlJsydsyqNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡土地所有权信息
     */
    List<QlfQlTdsyqDO> queryQlfQlTdsyqNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡宗地基本信息所有权信息
     */
    List<KttZdjbxxDO> queryKttZdjbxxNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡异议信息
     */
    List<QlfQlYydjDO> queryQlfQlYydjNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取过渡预告信息
     */
    List<QlfQlYgdjDO> queryQlfQlYgdjNoBdcdyhList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取房屋信息
     */
    List<QltFwFdcqYzDO> queryQltFwFdcqYzNoBdcdyhList(Map map);
}
