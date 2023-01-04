package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.DianService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021-08-30
 * @description 南通电过户信息接口
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface DianFeignService extends DianService {
}
