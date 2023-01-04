package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/07/22/9:14
 * @Description:
 */
public interface BdcGgMapper {

    /**
     * 批量更新不动产状态
     * @param ggids 公告Id集合
     * @return
     */
    int batchUpdatebdcggzt(@Param("ggids") List<String> ggids, @Param("ggzt") String ggzt);

    /**
     * 根据公告ID查询公告关联的流程信息
     * @param ggid 公告ID
     */
    List<BdcXmDO> queryBdcGgGlXmxx(@Param("ggid") String ggid);
}
