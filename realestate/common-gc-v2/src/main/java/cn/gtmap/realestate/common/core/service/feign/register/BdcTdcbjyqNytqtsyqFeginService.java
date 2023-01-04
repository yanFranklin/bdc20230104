package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcTdcbjyqNydqtsyqRestService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcTdcbjyqNytqtsyqFeginService extends BdcTdcbjyqNydqtsyqRestService {
}
