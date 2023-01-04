package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.building.core.bo.BdcxxConvertBO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-05
 * @description 同步不动产单元信息 Service
 */
public interface BdcdyxxService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyxxPlRequestDTO
     * @return void
     * @description 批量更新不动产单元信息
     */
    void updateBdcdyxxPl(BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyxxRequestDTO
     * @return void
     * @description 更新不动产单元信息
     */
    void updateBdcdyxx(BdcdyxxRequestDTO bdcdyxxRequestDTO) throws InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcxxConvertBO
     * @return void
     * @description 自动执行XML中的对照关系
     */
    void autoDozer(BdcxxConvertBO bdcxxConvertBO) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException;

}
