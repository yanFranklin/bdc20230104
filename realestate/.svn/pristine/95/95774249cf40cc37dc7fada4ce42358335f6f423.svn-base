package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.SjrptFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.hefei.BdcBjbhFeignService;
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
 * 蚌埠工作流实现类
 *
 * @author <a href="mailto:caolu@gtmap.com">caolu</a>
 * @version 3.0 2022/07/29
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_bengbu extends BdcWorkFlowAbstactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_bengbu.class);

    @Autowired
    private BdcBjbhFeignService bdcBjbhFeignService;

    @Autowired
    private SjrptFeignService sjrptFeignService;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("bengbu", this);
    }

    /**
     * 删除前执行事件
     * @param eventDTO
     * @param bdcXmDO
     * @param userName
     */
    @Override
    public void processBeforeDelete(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        // 删件时新增接口调用：4.3.4受理，推送不予受理信息给政务网,删件是blzt传0
        if (Objects.isNull(bdcXmDO)) {
            throw new AppException("蚌埠删除前执行事件，不动产项目为空");
        }
        //  政务取号成功推送,取号不成功不推送
        if (StringUtils.isNotBlank(bdcXmDO.getZfxzspbh())) {
            LOGGER.info("蚌埠删除前执行申报登记_删除信息推送，工作流实例id：{}，政府行政审批编号：{}", bdcXmDO.getGzlslid(), bdcXmDO.getZfxzspbh());
            bdcBjbhFeignService.sbdjSlxx(bdcXmDO.getGzlslid(), "0");
        } else {
            LOGGER.info("蚌埠删除前未政务推送，工作流实例id：{}", bdcXmDO.getGzlslid());
        }
        // 若金融平台推送的业务被认领删除后主动调“驳回互联网申请”接口，若领证方式是EMS的，登记系统获取到ems订单号，则调“推送ems信息”接口推送数据
        if (CommonConstantUtils.SPLY_SJYPT.equals(bdcXmDO.getSply()) && StringUtils.isNotBlank(bdcXmDO.getSpxtywh())){
            LOGGER.info("蚌埠删除前驳回互联网申请，工作流实例id：{}", bdcXmDO.getGzlslid());
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
