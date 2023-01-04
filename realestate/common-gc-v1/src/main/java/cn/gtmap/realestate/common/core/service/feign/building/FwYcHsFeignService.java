package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwYcHsRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 房屋预测户室相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface FwYcHsFeignService extends FwYcHsRestService {
}
