package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.ZdKDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.ZdKOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 16-9-2
 * @description 通过sql语句查询宗地空间属性
 */
@Component
public interface ZdkSqlMapper {
    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取宗地空间属性业务信息
     */
    List<ZdKDO> queryZdKList(Map map);

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取宗地空间属性业务信息
     */
    List<ZdKOldDO> queryZdKListOld(Map map);

    /**
     * @param map
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取海上构筑物空间属性业务信息
     */
    List<ZdKDO> queryGzwZhList(Map map);
}
