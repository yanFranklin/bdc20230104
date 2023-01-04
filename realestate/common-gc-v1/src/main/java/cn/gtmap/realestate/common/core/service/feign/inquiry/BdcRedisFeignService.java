package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcRedisRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-11
 * @description
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcRedisFeignService extends BdcRedisRestService {
}
