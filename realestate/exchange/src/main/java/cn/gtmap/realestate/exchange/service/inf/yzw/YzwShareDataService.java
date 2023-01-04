package cn.gtmap.realestate.exchange.service.inf.yzw;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-09-05
 * @description 一张网相关服务
 */
public interface YzwShareDataService {


    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bh 一张网办件编号
     * @param infApplyList 办件基本信息
     * @param infApplyProcessList 办件过程信息
     * @param infApplyResultList 办件结果信息
     * @param infApplyMaterialList 办件附件信息
     * @param infApplyJgzzList 办件结果证照信息
     * @param infApplyWlxxList 办件物流信息
     * @return
     * @description 统一入口 先删后插入 办件信息
     */
    void delAndInsertInfData(String bh, List<InfApplyDO> infApplyList, List<InfApplyProcessDO> infApplyProcessList,
                             List<InfApplyResultDO> infApplyResultList, List<InfApplyMaterialDO> infApplyMaterialList,
                             List<InfApplyJgzzDO> infApplyJgzzList, List<InfApplyWlxxDO> infApplyWlxxList);

    /**
      * 删除时插入InfResul
      * @param
      * @return
      * @Date 2021/2/19
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    void delInsertInfResultData( List<InfApplyDO> infApplyList, List<InfApplyResultDO> infApplyResultList);
}
