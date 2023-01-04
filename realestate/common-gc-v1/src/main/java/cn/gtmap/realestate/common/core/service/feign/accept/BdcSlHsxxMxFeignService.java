package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlHsxxMxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 受理核税信息明细
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlHsxxMxFeignService extends BdcSlHsxxMxRestService {
}
