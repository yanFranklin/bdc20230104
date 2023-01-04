package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPjqRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 受理评价器feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-08 15:00
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlPjqFeignService extends BdcSlPjqRestService {
}
