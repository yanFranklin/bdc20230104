package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwXmxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 项目信息相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface FwXmxxFeignService extends FwXmxxRestService {
}
