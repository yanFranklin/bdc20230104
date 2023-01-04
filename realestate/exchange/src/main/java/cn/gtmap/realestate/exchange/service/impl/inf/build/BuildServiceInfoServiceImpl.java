package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.bo.xsd.ServiceInfoBO;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-15
 * @description 构造 服务基本信息的 服务
 */
@Service(value = "buildServiceInfo")
public class BuildServiceInfoServiceImpl implements BuildRequestService{

    /**
     * @param builder
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过builder 处理 请求和响应
     */
    @Override
    public void build(InterfaceRequestBuilder builder) {
        if(builder.getExchangeBean() != null
                && builder.getExchangeBean().getRequestInfoBO() != null) {
            ServiceInfoBO serviceInfoBO = builder.getExchangeBean().getServiceInfoBO();
            // 获取 发送不同类型请求的Bean
            if (StringUtils.isNotBlank(serviceInfoBO.getRefBeanName())) {
                Object serviceBean = Container.getBean(serviceInfoBO.getRefBeanName());
                if (serviceBean != null && serviceBean instanceof InterfaceRequestService) {
                    builder.setInterfaceRequestService((InterfaceRequestService) serviceBean);
                }
            }
            // 获取 请求 基本属性
            builder.setRequestInfo(serviceInfoBO.getRequestInfo());
        }
    }
}
