package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;

/**
 * 将受理的信息对应到初始化模块对应的表中
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/7
 * @description 将受理的信息对应到初始化模块对应的表中
 */
public interface CopyAcceptIntoInitService {

    /**
     * 将受理信息中的项目信息覆盖到对应的项目表的记录中
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcSlxxDTO
     *@description 将受理信息中的项目信息覆盖到对应的项目表的记录中
     */
    void copyBdcSlXmxx(BdcSlxxDTO bdcSlxxDTO);

    /**
     *  将受理信息中的权利信息覆盖到对应的权利表的记录中
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcSlxxDTO
     *@description 将受理信息中的权利信息覆盖到对应的权利表的记录中
     */
    void copyBdcSlQlxx(BdcSlxxDTO bdcSlxxDTO);

}
