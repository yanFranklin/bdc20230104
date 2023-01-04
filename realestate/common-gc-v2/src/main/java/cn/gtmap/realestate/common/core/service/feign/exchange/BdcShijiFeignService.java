package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.BdcDaCxRestService;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcShijiRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zedeqiang@gtmap.com">zedq</a>
 * @version 1.0
 * @date 2021/04/26 14:22
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface BdcShijiFeignService extends BdcShijiRestService {
}
