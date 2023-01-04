package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcPrintRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/14
 * @description 不动产打印
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcPrintFeignService extends BdcPrintRestService {
}
