package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcFdcq3GyxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/15 19:27
 * @description
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcFdcq3GyxxFeignService extends BdcFdcq3GyxxRestService {
}
