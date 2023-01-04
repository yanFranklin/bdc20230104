package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.BdcXtJrRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 系统接入feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-06 16:28
 **/
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface BdcXtJrFeignService extends BdcXtJrRestService {
}
