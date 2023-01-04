package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcGzdjRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcGzdjFeignService extends BdcGzdjRestService {
}
