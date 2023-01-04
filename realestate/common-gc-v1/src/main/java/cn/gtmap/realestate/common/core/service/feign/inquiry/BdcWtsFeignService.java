package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcWtsRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
* @return
* @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
* @description 委托书feign服务
*/
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcWtsFeignService extends BdcWtsRestService {
}
