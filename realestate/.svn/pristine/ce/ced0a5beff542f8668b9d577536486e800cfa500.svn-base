package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlSjJkDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: realestate
 * @description: 工作流相关接口数据库交互
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 15:29
 **/
@Repository
public interface BdcGzlMapper {

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件
     * @date : 2022/3/30 15:42
     */
    List<BdcGzlSjDO> listBdcGzlsjByPage(BdcGzlQO bdcGzlQO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流接口
     * @date : 2022/3/30 16:15
     */
    List<BdcGzlJkDO> listBdcGzljkByPage(BdcGzlQO bdcGzlQO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和接口信息
     * @date : 2022/3/30 17:30
     */
    List<BdcGzlSjJkDTO> listGzlSjJk(BdcGzlQO bdcGzlQO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前流程事件配置的接口的最大数
     * @date : 2022/4/12 17:23
     */
    int queryMaxSxh(@Param("sjid") String sjid);
}
