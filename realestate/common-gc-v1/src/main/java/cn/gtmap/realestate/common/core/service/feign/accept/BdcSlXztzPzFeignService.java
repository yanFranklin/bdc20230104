package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXztzPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlXztzPzFeignService extends BdcSlXztzPzRestService {
}
