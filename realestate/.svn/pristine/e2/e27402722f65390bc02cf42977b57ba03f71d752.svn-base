package cn.gtmap.interchange.service;

import cn.gtmap.interchange.core.domain.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/9/25.
 * Time: 17:39
 * 推送权力阳光数据
 */
public interface PushQlygDataService {

    /**
     * 统一推送共享数据入口
     * @param infApplyList
     */
    void pushInfApplyData(List<InfApply> infApplyList);

    /**
     * 将共享数据插入至一张网前置库种
     * @param infApply
     */
    void insertQzk(InfApply infApply);
    
    void pushInfApplyProcess(List<InfApplyProcess> infApplyProcessList, List<InfApplyWlxx> infApplyWlxxList);

    void pushInfApplyResult(List<InfApplyResult> infApplyResultList);

    void pushInfApplyMaterial(List<InfApplyMaterial> infApplyMaterialList);

}
