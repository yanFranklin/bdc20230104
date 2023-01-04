package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcYcslDjywDzbRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/5
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcYcslDjywDzbFeignService extends BdcYcslDjywDzbRestService {
}
