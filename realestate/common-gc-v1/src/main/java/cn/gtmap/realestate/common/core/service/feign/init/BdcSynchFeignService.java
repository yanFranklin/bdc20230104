package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcSynchRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description 业务初始化接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcSynchFeignService extends BdcSynchRestService {
}
