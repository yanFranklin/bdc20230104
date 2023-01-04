package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcBdcZxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/6/7
 * @description 不动产单元相关服务FeignClient调用定义
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcBdcZxFeignService  extends BdcBdcZxRestService {
}
