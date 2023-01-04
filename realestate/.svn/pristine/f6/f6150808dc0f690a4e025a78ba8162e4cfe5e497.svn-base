package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.building.core.domain.SSjMaxBdcdyhDO;
import cn.gtmap.realestate.common.core.domain.exchange.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-07-13
 * @description
 */
public interface AccessBuildingMapper {

    List<KttFwCDO> queryKttFwCList(Map map);

    /**
     * 独幢层数据
     *
     * @param bdcdyh
     * @return
     * @Date 2022/6/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<KttFwCDO> queryKttFwCListDz(Map map);

    List<Map> queryKttFwZrzList(Map map);

    List<KttFwHDO> queryKttFwHListNotDz(Map map);

    List<KttFwHDO> queryKttFwHListIsDz(Map map);

    List<KttGyJzdDO> queryKttGyJzdList(Map map);

    List<KttGyJzxDO> queryKttGyJzxList(Map map);

    List<KttFwLjzDO> queryKttFwLjzList(Map map);

    List<ZhKDO> queryZhkList(Map map);

    /**
     * 根据宗地代码查询宗地变更记录表，按照更新时间倒序
     *
     * @param bh 宗地代码
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryZdbgjlbList(Map map);

    /**
     * 根据宗地代码查询宗地宗地空间属性
     *
     * @param bdcdyh
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<ZdKDO> queryZdkList(Map map);
}
