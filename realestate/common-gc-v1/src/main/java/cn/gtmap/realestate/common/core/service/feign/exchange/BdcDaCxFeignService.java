package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.BdcDaCxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zedeqiang@gtmap.com">zedq</a>
 * @version 1.0
 * @date 2021/04/26 14:22
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface BdcDaCxFeignService extends BdcDaCxRestService {
}
