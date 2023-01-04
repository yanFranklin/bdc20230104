package cn.gtmap.realestate.common.core.service.feign.portal;

import cn.gtmap.realestate.common.core.service.rest.portal.BdcPjgzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 派件规则服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2020/09/17
 */
@FeignClient(name = "portal-ui")
public interface BdcPjgzFeignService extends BdcPjgzRestService {
}
