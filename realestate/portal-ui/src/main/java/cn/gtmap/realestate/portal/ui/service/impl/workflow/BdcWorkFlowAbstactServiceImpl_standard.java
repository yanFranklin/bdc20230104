package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BdcWorkFlowAbstactServiceImpl_standard extends BdcWorkFlowAbstactService {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("standard", this);
    }

    @Override
    public String isAbandon(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOS)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
                // 查封、解封类流程不做撤销申请业务
                if (CommonConstantUtils.QLLX_CF.equals(bdcXmDTO.getQllx())) {
                    return StringUtils.EMPTY;
                }
            }
            return "success";
        } else {
            // 一窗受理没有登记数据
            return StringUtils.EMPTY;
        }
    }
}
