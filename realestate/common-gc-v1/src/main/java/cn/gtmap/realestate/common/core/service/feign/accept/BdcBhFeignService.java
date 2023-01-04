package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcBhRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/1
 * @description 不动产编号rest服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcBhFeignService extends BdcBhRestService {
}
