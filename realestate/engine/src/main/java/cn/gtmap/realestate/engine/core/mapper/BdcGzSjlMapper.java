package cn.gtmap.realestate.engine.core.mapper;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/03/05
 * @description 规则子系统：规则数据流Mapper
 */
@Repository
public interface BdcGzSjlMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 执行SQL
     */
    List<LinkedHashMap<String, Object>> executeSql(@Param("sql") String sql);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 子规则ID
     * @description 查询子规则关联数据流
     */
    List<BdcGzSjlDO> queryBdcGzSjl(@Param("gzid") String gzid);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param gzid
     * @param sjlIdList
     * @return
     * @description 删除规则数据流
     */
    void deleteBdcGzSjlByCondition(@Param("gzid")String gzid, @Param("list") List<String> sjlIdList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param scjId
     * @param sjlCsIdList
     * @return
     * @description 删除规则数据流参数
     */
    void deleteBdcGzSjlCsByCondition(@Param("scjId")String scjId, @Param("list") List<String> sjlCsIdList);
}
