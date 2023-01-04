package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwXmxxBgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description
 */
@FeignClient("${app.services.building-app:building-app}")
public interface FwXmxxBgFeignService extends FwXmxxBgRestService {
}