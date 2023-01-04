package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwKRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface FwKFeignService extends FwKRestService{
}
