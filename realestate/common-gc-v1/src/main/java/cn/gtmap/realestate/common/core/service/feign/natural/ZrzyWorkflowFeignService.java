package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyWorkflowRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/13
 * @description 自然资源工作流事件
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyWorkflowFeignService extends ZrzyWorkflowRestService {
}
