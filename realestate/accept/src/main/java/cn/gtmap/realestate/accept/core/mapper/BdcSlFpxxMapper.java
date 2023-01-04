package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/12/15
 * @description 受理发票信息
 */
public interface BdcSlFpxxMapper {

    /**
     * @param sfxxidList 收费信息id
     * @author <a href="mailto:jifnei@gtmap.cn">jifnei</a>
     * @description  根据参数批量删除发票信息
     */
    void batchDeleteBdcSlFpxxBySfxxid(@Param("sfxxidList") List<String> sfxxidList);
}
