package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcJsPzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description 角色配置
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcJsPzFeignService extends BdcJsPzRestService {
}
