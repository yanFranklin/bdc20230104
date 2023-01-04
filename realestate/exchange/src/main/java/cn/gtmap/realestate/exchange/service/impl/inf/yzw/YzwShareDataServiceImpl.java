package cn.gtmap.realestate.exchange.service.impl.inf.yzw;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwShareDataService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-06-26
 * @description 一张网相关服务
 */
@Service
@Validated
public class YzwShareDataServiceImpl implements YzwShareDataService {

    @Autowired
    @Qualifier("sjptEntityMapper")
    private EntityMapper sjptEntityMapper;

    /**
     * @param bh                   一张网办件编号
     * @param infApplyList         办件基本信息
     * @param infApplyProcessList  办件过程信息
     * @param infApplyResultList   办件结果信息
     * @param infApplyMaterialList 办件附件信息
     * @param infApplyJgzzList     办件结果证照信息
     * @param infApplyWlxxList     办件物流信息
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 统一入口 先删后插入 办件信息
     */
    @Override
    @Transactional(value = "sjpt", rollbackFor = Exception.class)
    public void delAndInsertInfData(String bh, List<InfApplyDO> infApplyList, List<InfApplyProcessDO> infApplyProcessList, List<InfApplyResultDO> infApplyResultList, List<InfApplyMaterialDO> infApplyMaterialList, List<InfApplyJgzzDO> infApplyJgzzList, List<InfApplyWlxxDO> infApplyWlxxList) {
        if (CollectionUtils.isNotEmpty(infApplyList)) {
            //inf_apply 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyList, InfApplyDO.class);
            //inf_apply_process 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyProcessList, InfApplyProcessDO.class);
            //inf_apply_result 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyResultList, InfApplyResultDO.class);
            //inf_apply_material 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyMaterialList, InfApplyMaterialDO.class);
            //inf_apply_jgzz 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyJgzzList, InfApplyJgzzDO.class);
            //inf_apply_wlxx 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyWlxxList, InfApplyWlxxDO.class);
        }
    }

    /**
     * 删除时插入InfResul
     *
     * @param infApplyList
     * @param infApplyResultList
     * @return
     * @Date 2021/2/19
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void delInsertInfResultData(List<InfApplyDO> infApplyList, List<InfApplyResultDO> infApplyResultList) {
        if (CollectionUtils.isNotEmpty(infApplyList)) {
            //inf_apply_result 先删后插
            delAndInsertInfApplyInfo(infApplyList, infApplyResultList, InfApplyResultDO.class);
        }
    }


    private void delAndInsertInfApplyInfo(List<InfApplyDO> infApplyList, List infoList, Class clazz) {
        if (CollectionUtils.isNotEmpty(infoList)) {
            for (InfApplyDO infApply : infApplyList) {
                Example example = new Example(clazz);
                example.createCriteria().andEqualTo("internalNo", infApply.getInternalNo());
                sjptEntityMapper.deleteByExampleNotNull(example);
            }
            List<List> partList= ListUtils.subList(infoList,500);
            for(List data:partList){
                sjptEntityMapper.insertBatchSelective(data);
            }
        }
    }
}
