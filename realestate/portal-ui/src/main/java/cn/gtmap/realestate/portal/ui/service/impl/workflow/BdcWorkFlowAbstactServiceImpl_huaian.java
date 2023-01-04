package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/10/24/17:02
 * @Description:
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_huaian extends BdcWorkFlowAbstactService {

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;
    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("huaian", this);
    }

    @Override
    public void processAfterDelete(BdcXmDO bdcXmDO, String userName, String reason) {
        //删除时记录操作日志
        bdcCzrzFeignService.addScCzrzWithOpinionWithXmxx(bdcXmDO.getGzldyid(), reason,bdcXmDO);
    }
}
