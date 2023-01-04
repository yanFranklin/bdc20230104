package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 房屋房产权利人变更
 */
public interface FwFcQlrBgService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwIndex
     * @param yfwFcqlrDOList
     * @param newFwFcQlrList
     * @return void
     * @description 处理权利人
     */
    void dealQlr(String bgbh,String fwIndex,List<FwFcqlrDO> yfwFcqlrDOList,List<FwFcqlrDO> newFwFcQlrList);
}
