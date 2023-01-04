package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcYwblBahdDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 业务办理备案核对service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 14:28
 **/
public interface BdcYwblBahdService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/21 14:28
     */
    List<BdcYwblBahdDO> listYwblBahd(String gzlslid);

    /**
     * @param bdcYwblBahdDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/21 14:29
     */
    BdcYwblBahdDO insertYwblBahd(BdcYwblBahdDO bdcYwblBahdDO);
}
