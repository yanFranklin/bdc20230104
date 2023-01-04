package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/30
 * @description 受理抵押权信息
 */
public interface BdcSlDyaqMapper {

    /**
     * @param bdcSlDeleteCsDTO 受理信息删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理抵押信息
     */
    void deleteBdcSlDyaq(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param map 更新信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理抵押权冗余字段
     */
    void updateBdcSlDyaqRyzdPl(Map map);
}
