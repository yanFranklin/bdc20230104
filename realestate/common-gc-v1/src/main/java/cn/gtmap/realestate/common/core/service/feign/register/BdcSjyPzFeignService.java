package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcSjyPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;
/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/9
 * @description 配置数据源接口
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcSjyPzFeignService extends BdcSjyPzRestService {
}
