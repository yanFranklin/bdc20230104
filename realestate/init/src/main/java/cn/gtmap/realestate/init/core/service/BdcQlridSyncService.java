package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.qo.init.BdcQlridSyncQO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/12
 * @description
 */
public interface BdcQlridSyncService {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除权利人同步
     * 删除权利人时,同时删除qlrid关联的表
     */
    void deleteQlrSync(BdcQlridSyncQO bdcQlridSyncQO);
}
