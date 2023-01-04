package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.exchange.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/14
 * @description
 */
public interface AccessBuildingService {

    List<KttFwCDO> queryKttFwCList(String bdcdyh);

    /**
     * 独幢层数据
     *
     * @param bdcdyh
     * @return
     * @Date 2022/6/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<KttFwCDO> queryKttFwCListDz(String bdcdyh);

    List<Map> queryKttFwZrzList(String bdcdyh);

    List<KttFwHDO> queryKttFwHListNotDz(String bdcdyh);

    List<KttFwHDO> queryKttFwHListIsDz(String bdcdyh);

    List<KttGyJzdDO> queryKttGyJzdList(String bdcdyh);

    List<KttGyJzxDO> queryKttGyJzxList(String bdcdyh);

    List<KttFwLjzDO> queryKttFwLjzList(String bdcdyh);

    List<ZhKDO> queryZhkList(String bdcdyh);

    /**
     * 根据宗地代码查询宗地变更记录表，按照更新时间倒序
     *
     * @param bh 宗地代码
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryZdbgjlbList(String bh);

    /**
     * 根据宗地代码查询宗地宗地空间属性
     *
     * @param bdcdyh
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<ZdKDO> queryZdkList(String bdcdyh);
}
