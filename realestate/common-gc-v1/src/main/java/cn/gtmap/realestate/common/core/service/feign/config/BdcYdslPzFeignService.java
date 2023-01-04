package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcYdslPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
 * @version 1.0, 2022/8/9
 * @description 异地受理相关接口
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcYdslPzFeignService extends BdcYdslPzRestService {
}
