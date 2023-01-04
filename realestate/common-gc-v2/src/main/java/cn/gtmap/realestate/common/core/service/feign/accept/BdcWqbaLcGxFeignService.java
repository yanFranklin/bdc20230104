package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcWqbaLcGxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 网签备案流程关系feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-16 10:50
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcWqbaLcGxFeignService extends BdcWqbaLcGxRestService {
}
