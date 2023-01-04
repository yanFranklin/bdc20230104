package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.common.core.domain.building.FwHstDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 变更  户室图相关服务
 */
public interface FwHstBgService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param yfwHstDO
     * @param newfwHstDO
     * @param deleteFlag
     * @return void
     * @description 处理户室图
     */
    void dealHst(FwHstDO yfwHstDO,FwHstDO newfwHstDO,boolean deleteFlag);
}
