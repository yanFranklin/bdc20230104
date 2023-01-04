package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description 宗地图特殊需求
 */
public interface TuxknrService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 是否需要读取 tuxknr 表
     */
    boolean needReadTuxknr();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO>
     * @description 根据DJH 查询 Tuxknr表
     */
    List<TuxknrResponseDTO> listTuxknrByDjh(String djh);
}
