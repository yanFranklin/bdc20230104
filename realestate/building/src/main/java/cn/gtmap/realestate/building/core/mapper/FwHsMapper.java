package cn.gtmap.realestate.building.core.mapper;


import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHouseIdDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description 房屋户室mapper
 */
public interface FwHsMapper {

    List<FwHsDO> listFwHs(Map<String,String> paramMap);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/22 10:52
     */
    List<FwhsQlrDTO> listFwHsWithQlr(Map<String,String> paramMap);
    /**
     * @param paramMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量更新房屋户室表
     */
    void batchUpdateFwhs(Map<String, Object> paramMap);

    /**
     * @param paramMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量替换房屋户室属性
     */
    void replaceUpdateFwhs(Map<String, Object> paramMap);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍调查表房屋信息
     */
    DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(String bdcdyh);

    /**
     * @param map
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询房屋户室
     */
    List<FwHsDO> listFwHsByPageOrder(Map map);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 查询户室信息
     * @date : 2022/5/16 15:14
     */
    List<Map> listFwhsByHouseId(Map map);

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 根据zl查询户室信息
    */
    List<Map> listFwhsByZl(Map map);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据bdcdyhList查询户室流程状态
     */
    List<String> queryFwhsLcztByBdcdyh(@Param("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param bdcTddysfxxQO 更新土地抵押释放信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新土地抵押释放信息
     */
    void updateFwhsTddysfxx(BdcTddysfxxQO bdcTddysfxxQO);

    /**
     * @description 根据不动产单元号查询户室信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/18 15:45
     * @param paramMap
     * @return List<FwHsHouseIdDTO>
     */
    List<FwHsHouseIdDTO> listFwhsHouseIdByBdcdyh(Map<String, String> paramMap);
}
