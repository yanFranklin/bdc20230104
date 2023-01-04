package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.BdcSjptRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0
 * @date 2022/7/18 16:25
 * @description 省级平台接口调用
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface BdcSjptFeignService extends BdcSjptRestService {
}
