package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.ZdJtcyRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description 宗地家庭成员
 */
@FeignClient("${app.services.building-app:building-app}")
public interface ZdJtcyFeignService extends ZdJtcyRestService {
}
