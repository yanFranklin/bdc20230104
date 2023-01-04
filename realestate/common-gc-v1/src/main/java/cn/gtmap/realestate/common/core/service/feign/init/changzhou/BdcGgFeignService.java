package cn.gtmap.realestate.common.core.service.feign.init.changzhou;

import cn.gtmap.realestate.common.core.service.rest.init.changzhou.BdcGgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 不动产公告feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 10:06
 **/
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcGgFeignService extends BdcGgRestService {
}
