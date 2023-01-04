package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/03/06
 * @description
 */
public interface DjhZtService {

    /**
     * @param bdcdyh
     * @param isjd
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询djh冻结信息
     */
    DjhZtResponseDTO getDjhZtByBdcdyh(String bdcdyh,String isjd);

    /**
     * @param djh
     * @param isjd
     * @return DjhZtResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 查看地籍号状态
     */
    DjhZtResponseDTO getDjhZtByDjh(String djh,String isjd);

    /**
     * @param djhList
     * @param isjd
     * @return List<DjhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据djh查询冻结信息
     */
    List<DjhZtResponseDTO> listDjhZtByDjh(List<String> djhList,String isjd);


}
