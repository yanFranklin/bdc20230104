package cn.gtmap.realestate.engine.core.mapper;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/5
 * @description
 */
@Repository
public interface BdcGzZhGzMapper {

    /**
     * 查询zhbs条数
     *
     * @param bdcGzZhgzDO bdcGzZhgzDO
     * @return int
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    int countZhbs(BdcGzZhgzDO bdcGzZhgzDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhGzQO 验证查询参数
     * @return {List} 组合标识集合
     * @description 查询组合规则标识信息
     */
    List<String> listBdcGzZhgzBs(BdcGzZhGzQO bdcGzZhGzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 强制验证对应的组合标识与子规则关系
     * @description 查询强制验证对应的组合标识与子规则关系信息
     */
    List<BdcGzGxDO> listBdcGzQzyzGx();

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhuyong</a>
     * @return {List} BdcGzZhgzDO
     * @description
     */
    List<BdcGzZhgzDO> listZhGzByZhBsList( @Param("zhBsList") List<String> zhBsList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhuyong</a>
     * @return {List} BdcGzZhgzDO
     * @description
     */
    List<BdcGzZhgzDO> listZhGzNotReleatedZgz();
}
