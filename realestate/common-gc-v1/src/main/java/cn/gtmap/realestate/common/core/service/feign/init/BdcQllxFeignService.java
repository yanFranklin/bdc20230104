package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcQllxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/3
 * @description 业务初始化不动产权利信息接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcQllxFeignService extends BdcQllxRestService {
}
