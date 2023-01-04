package cn.gtmap.realestate.common.core.service.feign.rules;

import cn.gtmap.realestate.common.core.service.rest.rules.BdcGzXzYzLwRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/16,1.0
 * @description
 */
@FeignClient("rules-app")
public interface BdcGzXzYzLwFeignService extends BdcGzXzYzLwRestService {
}
