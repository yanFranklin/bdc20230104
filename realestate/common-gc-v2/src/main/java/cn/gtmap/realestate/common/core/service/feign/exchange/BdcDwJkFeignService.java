package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.BdcDwJkRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/24 14:22
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface BdcDwJkFeignService extends BdcDwJkRestService {
}
