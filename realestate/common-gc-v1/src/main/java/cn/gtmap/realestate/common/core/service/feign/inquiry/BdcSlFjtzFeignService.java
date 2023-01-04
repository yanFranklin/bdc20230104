package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSlFjtzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author wangyinghao
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcSlFjtzFeignService extends BdcSlFjtzRestService {
}
