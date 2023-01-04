package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.DjhZtRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/3/6
 * @description 地籍号状态服务
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface DjhZtFeignService extends DjhZtRestService {
}
