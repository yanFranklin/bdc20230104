package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/14
 * @description 面积计算相关service
 */
public interface CalculatedAreaService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @description 计算逻辑幢建筑面积
     */
    void calculatedLjzJzmj(LjzJzmjRequestDTO ljzJzmjRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积
     */
    Integer calculatedFttdmj(FttdmjRequestDTO fttdmjRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积(配置)
     */
    void calculatedFttdmjByConfig(FttdmjRequestDTO fttdmjRequestDTO);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return java.lang.String
     * @description 计算逻辑幢建筑面积(配置)
     */
    void calculatedLjzJzmjByConfig(LjzJzmjRequestDTO ljzJzmjRequestDTO);

    /**
     * @param fttdmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByDTO(FttdmjRequestDTO fttdmjRequestDTO);
    /**
     * @param ljzJzmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByDTO(LjzJzmjRequestDTO ljzJzmjRequestDTO);
}
