package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcDyhGzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 批量更新不动产单元服务
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcDyhGzFeignService extends BdcDyhGzRestService {
}
