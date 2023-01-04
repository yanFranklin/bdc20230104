package cn.gtmap.realestate.exchange.core.mapper.qj;

import cn.gtmap.realestate.exchange.core.domain.exchange.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-07-13
 * @description
 */
public interface AccessBuildingMapper {

    List<KttFwCDO> queryKttFwCList(Map map);

    List<Map> queryKttFwZrzList(Map map);

    List<KttFwHDO> queryKttFwHListNotDz(Map map);

    List<KttFwHDO> queryKttFwHListIsDz(Map map);

    List<KttGyJzdDO> queryKttGyJzdList(Map map);

    List<KttGyJzxDO> queryKttGyJzxList(Map map);

    List<KttFwLjzDO> queryKttFwLjzList(Map map);

    List<ZhKDO> queryZhkList(Map map);

}
