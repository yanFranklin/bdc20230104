package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.BuildingConfigRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/8/3
 * @description 配置服务
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface BuildingConfigFeignService extends BuildingConfigRestService {
}
