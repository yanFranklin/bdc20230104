package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcWtsRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description 委托书
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcWtsFeignService extends BdcWtsRestService {
}
