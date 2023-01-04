package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;

import java.util.List;

/**
 * 房地产权多幢服务
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface BdcFdcqFdcqxmService {

    /**
     * 通过房地产权id获取房地产权多幢项目信息(根据幢号和总层数排序)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param qlid 房地产权id
     *@return List<BdcFdcqFdcqxmDO>
     *@description
     */
    List<BdcFdcqFdcqxmDO> queryFdcqxmListByQlid(String qlid);

    /**
     * 根据权利ID批量更新房地产权（项目内多幢）项目信息
     * @param qlid 权利ID
     * @param bdcFdcqFdcqxmDO 权利信息
     * @return 更新数量
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    int updateFdcqxmByQlid(String qlid, BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO);
}
