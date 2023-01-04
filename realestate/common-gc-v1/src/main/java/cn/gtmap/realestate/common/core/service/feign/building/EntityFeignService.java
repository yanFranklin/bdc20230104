package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.EntityRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-18
 * @description
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface EntityFeignService extends EntityRestService {
}
