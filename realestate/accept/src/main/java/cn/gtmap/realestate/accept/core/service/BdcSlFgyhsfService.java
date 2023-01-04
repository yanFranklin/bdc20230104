package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept
        .BdcSlFgyhsfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/9:31
 * @Description:
 */
public interface BdcSlFgyhsfService {

    /**
     * @param bdcSlFgyhsfDO 不动产受理房改优惠售房信息
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 新增不动产受理房屋信息
     */
    Integer saveOrUpdateBdcSlFgyhsf(BdcSlFgyhsfDO bdcSlFgyhsfDO);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 根据工作流实例id获取不动产受理房屋信息
     */
    BdcSlFgyhsfDO getBdcSlFgyhsf(String gzlslid);

    BdcSlFghysfDTO getBdcSlFghysfDTO(String gzlslid);
}
