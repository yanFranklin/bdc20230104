package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlDjMlDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/8
 * @description 不动产登记簿权利Mapper
 */
public interface BdcBdcdjbMapper {
    /**
     * @param map 查询map
     * @return List<BdcQlDJMlDTO> 不动产权利登记目录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产登记权利目录（因为已经有分页查询，所以采用map传值，规范要求尽量用查询对象）
     */
    List<BdcQlDjMlDTO> listBdcQlDjMlByPage(Map map);

    /**
     * @param gzlslid 工作流实例ID
     * @return int 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新宗地基本信息
     */
    int updateZdDjbxx(DbxxQO gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return int 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新宗海基本信息
     */
    int updateZhDjbxx(DbxxQO gzlslid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param param SQL参数（项目相关信息）
     * @description 房屋、林地如果对应宗地登记时间信息为空则更新一下
     */
    int updateZdJbxxWhenDjsjIsNull(BdcXmDO param);
}
