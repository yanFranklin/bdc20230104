package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcQjdcRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @param null
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查feign服务
 * @date : 2021/8/5 13:43
 */

@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcQjdcFeignService extends BdcQjdcRestService {
}
