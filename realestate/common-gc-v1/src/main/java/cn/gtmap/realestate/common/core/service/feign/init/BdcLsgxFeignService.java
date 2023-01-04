package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcLsgxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description 匹配接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcLsgxFeignService extends BdcLsgxRestService {
}
