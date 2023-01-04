package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcdyZtRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元状态服务
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcdyZtFeignService extends BdcdyZtRestService {
}
