package cn.gtmap.realestate.common.core.service.feign.register;

import cn.gtmap.realestate.common.core.service.rest.register.BdcXtMryjRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/29
 * @description 默认意见接口
 */
@FeignClient(name = "${app.services.register-app:register-app}")
public interface BdcXtMryjFeignService extends BdcXtMryjRestService {
}
