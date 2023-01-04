package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface BdcPlcxMapper {
    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [BdcPlcxQO]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询
     */
    List<BdcYcPlcxDTO> listBdcYcPlcx(BdcPlcxQO BdcPlcxQO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [BdcPlcxQO]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询 同时查询房屋和土地
     */
    List<BdcYcPlcxDTO> listBdcYcPlcxAll(BdcPlcxQO BdcPlcxQO);

    /**
     * 查询出产权下一手项目信息
     * @param bdcqzhSet 历史产权证号集合
     * @return 下一手项目
     */
    List<BdcXmDO> listXysxmByBdcqzh(@Param("set")Set<String> bdcqzhSet);
}
