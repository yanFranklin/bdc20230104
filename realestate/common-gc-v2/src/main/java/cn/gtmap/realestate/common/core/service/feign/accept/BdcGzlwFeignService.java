package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcGzlwRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcGzlwFeignService extends BdcGzlwRestService {
}
