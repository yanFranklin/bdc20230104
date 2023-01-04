package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.ZhRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 宗海相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface ZhFeignService extends ZhRestService {
}
