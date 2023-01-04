package cn.gtmap.realestate.init.service.tshz;

import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

/**
 * 特殊后置处理接口
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
public interface InitBdcTsHzService {

    /**
     * 特殊后置处理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param initServiceQO
     * @param initServiceDTO
     *@return InitServiceDTO
     *@description
     */
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception;
}
