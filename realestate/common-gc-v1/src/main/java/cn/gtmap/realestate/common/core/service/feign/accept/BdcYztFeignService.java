package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcYztRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:chneyucheng@gtmap.cn">chneyucheng</a>
 * @version 1.0, 2020/12/18
 * @description  一张图服务feignRestService
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcYztFeignService extends BdcYztRestService {
}
