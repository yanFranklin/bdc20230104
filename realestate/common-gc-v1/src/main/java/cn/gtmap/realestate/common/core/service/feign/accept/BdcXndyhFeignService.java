package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcXndyhRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/28
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcXndyhFeignService extends BdcXndyhRestService {
}
