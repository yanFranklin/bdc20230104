package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/6
 * @description 将不同模块的初始化参数转化为初始化模块的标准入参格式
 */
public interface RegularInitParameterService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcSlxxDTO
     *@return List<InitServiceQO>
     *@description 将受理模块传来的参数转换为初始化模块的标准入参
     */
    List<InitServiceQO> changeAcceptDTOIntoInitQO(BdcSlxxDTO bdcSlxxDTO);
}
