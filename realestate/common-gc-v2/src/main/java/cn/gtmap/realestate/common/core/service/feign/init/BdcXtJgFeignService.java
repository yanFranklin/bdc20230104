package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcXtJgRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/3
 * @description 业务初始化不动产权利人信息接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcXtJgFeignService extends BdcXtJgRestService {
}
