package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcXtbbRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 系统版本服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 16:43
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcXtBbFeignService extends BdcXtbbRestService {
}
