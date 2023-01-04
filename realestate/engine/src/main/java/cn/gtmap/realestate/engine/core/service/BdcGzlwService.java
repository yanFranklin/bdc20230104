package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/26
 * @description
 */
public interface BdcGzlwService {
    /**
     * 保存例外审核信息
     * @param bdcGzlwShDO 例外信息
     */
    void addShxxData(BdcGzlwShDO bdcGzlwShDO);

    void updateBdcGzlwShzt(String processInsId, String currentUserName);

    /**
     * @param bdcGzlwShDOList 规则例外数据
     * @param slbh 受理编号
     * @param lwyy 例外原因
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量添加审核信息
     */
    BdcGzlwShDO addShxxDataPl(List<BdcGzlwShDO> bdcGzlwShDOList, String slbh, String lwyy);

    /**
     * @param slbh 受理编号
     * @return 例外审核记录
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号获取例外审核记录
     */
    List<BdcGzlwShDO> listBdcGzlwShBySlbh(String slbh);

}
