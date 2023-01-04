package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.SjrptFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 宣城工作流实现类
 *
 * @author <a href="mailto:caolu@gtmap.com">caolu</a>
 * @version 3.0 2022/07/29
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_xuancheng extends BdcWorkFlowAbstactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_xuancheng.class);
    

    @Autowired
    private SjrptFeignService sjrptFeignService;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("xuancheng", this);
    }

    /**
     * 删除前执行事件
     * @param eventDTO
     * @param bdcXmDO
     * @param userName
     */
    @Override
    public void processBeforeDelete(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        // 删件
        if (Objects.isNull(bdcXmDO)) {
            throw new AppException("宣城删除前执行事件，不动产项目为空");
        }
        // 若金融平台推送的业务被认领删除后主动调“驳回互联网申请”接口，若领证方式是EMS的，登记系统获取到ems订单号，则调“推送ems信息”接口推送数据
        if (CommonConstantUtils.SPLY_SJYPT.equals(bdcXmDO.getSply()) && StringUtils.isNotBlank(bdcXmDO.getSpxtywh())){
            LOGGER.info("宣城删除前驳回互联网申请，工作流实例id：{}", bdcXmDO.getGzlslid());
            sjrptDeleteMethod(eventDTO,bdcXmDO,userName);
        }

    }

    /**
     * 省金融平台业务删除时接口调用
     *
     * @param eventDTO
     * @param bdcXmDO
     * @param userName
     */
    private void sjrptDeleteMethod(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        // 驳回互联网申请
        sjrptFeignService.bhHlwSq(bdcXmDO.getSpxtywh(),eventDTO.getReason(),bdcXmDO.getGzlslid());
    }
}
