package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.AcceptWorkFlowService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/9
 * @description 添加购物车数据处理公共服务
 */
@Service
public class AcceptWorkFlowRestServiceImpl implements AcceptWorkFlowService {

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    BdcSlSfxxService bdcSlSfxxService;

    @Override
    public void updateYjjffph(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        // 1、判断流程是否收费
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            return;
        }
        // 获取权利人收费信息
        bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(t-> t.getQlrlb().equals(CommonConstantUtils.QLRLB_QLR)).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            // 获取配置的月结银行名称
            List<String> yjyhmcList = this.bdcXtJgFeignService.listYjyhmc();
            yjyhmcList.add("李傲翔");
            if(CollectionUtils.isEmpty(yjyhmcList)){
                return;
            }
            // 2、判断流程是否月结缴费
            for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList){
                if(yjyhmcList.contains(bdcSlSfxxDO.getJfrxm()) && StringUtils.isBlank(bdcSlSfxxDO.getFph())){
                    // 3、发票号为‘月结’
                    bdcSlSfxxDO.setFph("月结");
                    this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

}
