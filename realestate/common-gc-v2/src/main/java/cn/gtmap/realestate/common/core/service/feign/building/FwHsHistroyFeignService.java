package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwHsHistroyRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/4
 * @description
 */
@FeignClient("${app.services.building-app:building-app}")
public interface FwHsHistroyFeignService extends FwHsHistroyRestService {
}
