package cn.gtmap.realestate.exchange.core.mapper.exchange;

import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/6/21
 * @description 插入升腾状态队列
 */
public interface SyncDataMapper {
    /**
     * @param param 同步数据
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/6/21
     * @description
     */
    void insertSyncData(Map param);

    /**
     * @param param 同步数据
     * @return 更新项目状态
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/6/21
     * @description
     */
    void updateSyncDataXmzt(Map param);

    /**
     * @param key 业务号、项目状态
     * @return
     * @author <a href="mailto:liuyu@gtmap.cn">liuyu</a>
     * @version 1.0, 2017/2/212
     * @description 根据业务号查找符合条件的数据
     */
    Map getSyncDataByYwh(Map key);
}
