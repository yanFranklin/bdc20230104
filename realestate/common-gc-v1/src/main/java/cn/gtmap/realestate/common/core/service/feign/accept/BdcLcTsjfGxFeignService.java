package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcLcTsjfRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 流程与推送缴费关系
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 13:48
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcLcTsjfGxFeignService extends BdcLcTsjfRestService {
}
