package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description 与第三方接口交互服务
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface ExchangeInterfaceFeignService extends ExchangeInterfaceRestService {

}
