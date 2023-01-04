package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDjxlSbdjfkCsgxDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href=""mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>
 * @version 2020-12-15
 * @description
 */
public interface BdcDjxlSbdjfkCsGxMapper {

    /**
     * 根据登记小类查询参数
     * @param djxl
     * @return
     */
    BdcDjxlSbdjfkCsgxDO querySbdjfkCsByDjxl(@Param("djxl") String djxl, @Param("qjgldm") String qjgldm);
}
