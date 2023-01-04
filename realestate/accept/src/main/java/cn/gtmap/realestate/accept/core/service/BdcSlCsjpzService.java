package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 长三角配置service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 14:09
 **/
public interface BdcSlCsjpzService {
    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询服务
     * @date : 2022/5/10 14:10
     */
    List<BdcSlCsjpzDO> listCsjpz(BdcSlCsjpzDO bdcSlCsjpzDO);

    /**
     * @param pzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除服务
     * @date : 2022/5/10 14:10
     */
    int deletCsjpz(String pzid);

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存服务
     * @date : 2022/5/10 14:11
     */
    BdcSlCsjpzDO saveCsjpz(BdcSlCsjpzDO bdcSlCsjpzDO);
}
