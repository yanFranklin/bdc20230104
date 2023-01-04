package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/12/24 09:07
 * @description 评价器签字信息service
 */
public interface BdcQzxxService {
    /**
     * @param bdcQzxxDO bdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 保存签字信息
     */
    BdcQzxxDO insertBdcQzxx(BdcQzxxDO bdcQzxxDO);

    /**
     * @param bdcQzxxDO 评价器签字Do
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 更新不动产评价器签字信息
     */
    Integer updateBdcQzxx(BdcQzxxDO bdcQzxxDO);

    /**
     * 查询签字信息
     *
     * @param bdcQzxxDO bdcQzxxDO
     * @return BdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcQzxxDO> queryBdcQzxx(BdcQzxxDO bdcQzxxDO);
}
