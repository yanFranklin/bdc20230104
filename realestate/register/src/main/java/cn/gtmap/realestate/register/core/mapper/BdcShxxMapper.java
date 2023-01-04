package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/1/13
 * @description 不动产审核信息Mapper
 */
public interface BdcShxxMapper {
    /**
     * @param shxxidList 审核信息IDList
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核签名信息
     */
    int batchDeleteShxxSign(List<String> shxxidList);

    /**
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param map
     * @return 初审的最后一次审核信息
     * @description  初审的最后一次审核信息
     */
    List<BdcShxxDO> queryCsShr(Map map);
}
