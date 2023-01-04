package cn.gtmap.realestate.common.core.service.feign.inquiry;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcGxjkRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/4
 * @description 省级共享接口服务
 */
@FeignClient(name = "${app.services.inquiry-app:inquiry-app}")
public interface BdcGxjkFeignService extends BdcGxjkRestService {
}
