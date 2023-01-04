package cn.gtmap.realestate.common.core.service.feign.etl;

import cn.gtmap.realestate.common.core.service.rest.etl.BdcHtbaRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "${app.services.etl-app:etl-app}")
public interface BdcHtbaFeginService extends BdcHtbaRestService {
}
