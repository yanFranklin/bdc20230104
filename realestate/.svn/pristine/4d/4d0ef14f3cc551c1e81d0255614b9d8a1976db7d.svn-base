package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程与推送缴费关系数据服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 13:56
 **/
public interface BdcLcTsjfGxService {
    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询流程与推送缴费的关系
     * @date : 2021/9/16 13:56
     */
    List<BdcLcTsjfGxDO> listLcTsjfGx(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例id查询一条流程与推送缴费的关系
     */
    BdcLcTsjfGxDO queryOneLcTsjfGx(String gzlslid);

    /**
     * @param sfxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id查询流程推送缴费
     * @date : 2021/10/26 16:51
     */
    List<BdcLcTsjfGxDO> listLcTsjfGxBySfxxid(String sfxxid);

    /**
     * @param bdcLcTsjfGxDO 推送缴费与流程关系
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据查询参数查询流程推送缴费关系
     * @date : 2021/11/22
     */
    List<BdcLcTsjfGxDO> listLcTsjfGxByParam(BdcLcTsjfGxDO bdcLcTsjfGxDO);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除流程与推送缴费关系
     * @date : 2021/9/16 13:58
     */
    int deleteLcTsjfGx(String gzlslid);

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增流程与推送缴费关系数据
     * @date : 2021/9/16 13:59
     */
    int batchInsertLcTsjfGx(List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList);

    /**
     * @param sfxxidList 收费信息ID集合
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id 删除
     * @date : 2021/10/26 16:20
     */
    int deleteLcTsGxBySfxxid(List<String> sfxxidList, String gzlslid);

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/3 14:31
     */
    void batchUpdateLcTsjfGx(List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList);

    /**
     * 更新流程推送缴费关系
     * @param bdcLcTsjfGxDO 推送缴费与流程关系DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateLcTsjfGx(BdcLcTsjfGxDO bdcLcTsjfGxDO);

    /**
     * 清除推送ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void clearTsid(String gzlslid);
}
