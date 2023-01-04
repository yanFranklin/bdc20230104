package cn.gtmap.realestate.building.core.mapper;


import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-15
 * @description
 */
@Repository
public interface FwYchsMapper {
    /**
     * @Description: 查询预测户室
     * @Param:
     * @return:
     * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @Date:
     */
    List<FwYchsDO> listYchsByPageOrder(Map map);


    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/1/8 17:14
     */
    List<FwhsQlrDTO> listYchsWithQlr(Map map);

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 查询房屋预测户室信息
     * @date : 2022/5/16 15:20
     */
    List<Map> listFwYchsByHouseId(Map map);

    List<Map> listFwYchsByZl(Map map);

    /**
     * @param YSFWBM
     * @author wangyinghao
     * @description 根据YSFWBM 查询房屋预测户室信息
     * @date : 2022/5/16 15:20
     */
    List<FwYchsDO> listFwYchsByYsfwbm(@Param("YSFWBM") String YSFWBM);

}
