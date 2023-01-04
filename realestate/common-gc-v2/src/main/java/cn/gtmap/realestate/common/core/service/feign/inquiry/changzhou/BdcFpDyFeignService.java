package cn.gtmap.realestate.common.core.service.feign.inquiry.changzhou;

import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcFpDyRestService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcFpDyFeignService extends BdcFpDyRestService {
}
