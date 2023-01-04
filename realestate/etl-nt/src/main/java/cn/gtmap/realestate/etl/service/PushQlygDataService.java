package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.etl.core.domian.nantong.BnInfSpare;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/9/25.
 * Time: 17:39
 * 推送权力阳光数据
 */
public interface PushQlygDataService {


    void pushInfApply(List<InfApplyDO> infApplyList,Map<String,BnInfSpare> map);

    void pushInfApplyProcess(List<InfApplyProcessDO> infApplyProcessList, List<InfApplyWlxxDO> infApplyWlxxList,BnInfSpare bnInfSpare);

    void pushInfApplyResult(List<InfApplyResultDO> infApplyResultList,BnInfSpare bnInfSpare);

    void pushInfApplyJgzz(List<InfApplyJgzzDO> infApplyJgzzList);

    void pushInfApplyWlxx(List<InfApplyWlxxDO> infApplyWlxxList);

    void pushInfApplyWlxxToProcess(List<InfApplyWlxxDO> infApplyWlxxList);

    void pushInfApplyMaterial(List<InfApplyMaterialDO> infApplyMaterialList,BnInfSpare bnInfSpare);

    void pushInfApplyAccept(List<InfApplyProcessDO> infApplyProcessList);

    void pushInfApplyVerify(List<InfApplyVerifyDO> infApplyVerifyList);

}
