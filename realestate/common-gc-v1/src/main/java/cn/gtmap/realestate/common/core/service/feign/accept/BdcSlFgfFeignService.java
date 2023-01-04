package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFgfRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2020/8/21
 * @description 登记房改房服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlFgfFeignService extends BdcSlFgfRestService {
}
