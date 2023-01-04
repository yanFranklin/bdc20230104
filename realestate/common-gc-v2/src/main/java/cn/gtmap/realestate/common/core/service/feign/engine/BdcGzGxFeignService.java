package cn.gtmap.realestate.common.core.service.feign.engine;

import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzGxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description
 */
@FeignClient(name = "${app.services.engine-app:engine-app}")
public interface BdcGzGxFeignService extends BdcGzGxRestService {
}
