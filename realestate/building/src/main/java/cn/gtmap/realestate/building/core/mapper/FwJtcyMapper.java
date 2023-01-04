package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.FwJtcyDO;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/30/19:29
 * @Description: 房屋家庭成员
 */
public interface FwJtcyMapper {

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据权利人主键查询房屋家庭成员
     */
    List<FwJtcyDO> listFwJtcy(Map<String,Object> paramMap);
}
