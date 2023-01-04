package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcXtQlqtzkFjPzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 不动产权利其他状况、附记配置服务
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcXtQlqtzkFjPzFeignService extends BdcXtQlqtzkFjPzRestService {
}
