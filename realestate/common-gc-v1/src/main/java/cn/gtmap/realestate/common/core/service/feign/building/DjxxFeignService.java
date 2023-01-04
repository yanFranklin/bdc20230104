package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.DjxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-17
 * @description 地籍信息相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface DjxxFeignService extends DjxxRestService {
}
