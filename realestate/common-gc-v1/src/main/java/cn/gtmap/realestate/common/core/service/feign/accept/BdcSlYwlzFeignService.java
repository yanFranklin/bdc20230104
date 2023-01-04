package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYwlzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 业务流转feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 11:20
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlYwlzFeignService extends BdcSlYwlzRestService {
}
