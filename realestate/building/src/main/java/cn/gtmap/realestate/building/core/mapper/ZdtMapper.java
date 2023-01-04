package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.SSjZdtDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-23
 * @description  宗地图相关查询
 */
public interface ZdtMapper {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjZdtDO>
     * @description 根据  DJH 查询宗地图
     */
    List<SSjZdtDO> queryZdtByDjh(String djh);
}
