package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/6
 * @description 受理公共服务
 */
public interface BdcCommonSlService {

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据参数批量删除受理信息
     */
    void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

}
