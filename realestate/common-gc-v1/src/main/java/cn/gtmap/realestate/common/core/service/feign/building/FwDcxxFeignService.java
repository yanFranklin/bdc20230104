package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwDcxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
 * @description 房屋户室调查信息服务
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface FwDcxxFeignService extends FwDcxxRestService {
}
