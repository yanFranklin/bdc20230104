package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFgyhsfRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/10:17
 * @Description:
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlFgyhsfFeignService extends BdcSlFgyhsfRestService{
}
