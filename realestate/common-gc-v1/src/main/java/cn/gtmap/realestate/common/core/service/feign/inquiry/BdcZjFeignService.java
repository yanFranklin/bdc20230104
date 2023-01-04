package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZjRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/4/10
 * @description
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcZjFeignService extends BdcZjRestService{
}
