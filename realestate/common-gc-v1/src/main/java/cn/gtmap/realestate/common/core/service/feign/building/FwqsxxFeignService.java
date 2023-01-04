package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.AccessBuildingRestService;
import cn.gtmap.realestate.common.core.service.rest.building.FwqsxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-07-14
 * @description
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface FwqsxxFeignService extends FwqsxxRestService {
}
