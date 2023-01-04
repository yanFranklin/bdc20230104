package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/10/30
 * @description 房屋逻辑幢信息mapper
 */
public interface FwLjzMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @description 查询逻辑幢
     */
    List<FwLjzDO> listFwLjz(Map<String,String> paramMap);
    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [slbh] 受理编号
     * @return: String 匹配的受理编号
     * @description 通过FW_LJ左关联s_sj_bgsh匹配受理编号
     */
    String selectFwLjzLeftJoin(String slbh);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询房逻辑幢流程状态
     */
    List<String> queryLjzLcztByBdcdyh(@Param("bdcdyhList") List<String> bdcdyhList);
}
