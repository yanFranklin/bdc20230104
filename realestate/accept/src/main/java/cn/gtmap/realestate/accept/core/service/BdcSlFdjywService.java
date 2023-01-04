package cn.gtmap.realestate.accept.core.service;


import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/7/22
 * @description 受理非登记业务公共接口
 */
public interface BdcSlFdjywService extends InterfaceCode {

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化非登记业务信息
     */
     void initFdjywxx(BdcSlCshDTO bdcSlCshDTO);

}
