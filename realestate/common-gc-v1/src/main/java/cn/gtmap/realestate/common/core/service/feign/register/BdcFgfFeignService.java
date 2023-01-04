package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcFgfRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 登记房改房服务
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcFgfFeignService extends BdcFgfRestService {
}
