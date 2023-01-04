package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.RegisterWorkflowRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/27
 * @description 审核登簿工作流feign服务接口
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface RegisterWorkflowFeignService extends RegisterWorkflowRestService {
}
