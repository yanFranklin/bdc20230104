package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlrxxDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/28
 * @description 不动产权利人Mapper
 */
public interface BdcQlrMapper {
    /**
     * @param qlrGyqk 更新参数
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新同一个流程中同一个权利人的共有情况
     */
    int updateQlrGyqkPl(Map<String, Object> qlrGyqk);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    int updateQlrGyqk(BdcQlrDO bdcQlrDO);

    /**
     * @param qlrGyqk 更新参数
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新同一个权利人的共有情况
     */
    int batchUpdateQlrGyqk(Map<String, Object> qlrGyqk);

}
