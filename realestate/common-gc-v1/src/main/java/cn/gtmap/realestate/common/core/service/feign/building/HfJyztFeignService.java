package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.HfJyztRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-23
 * @description 合肥交易状态相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface HfJyztFeignService extends HfJyztRestService{
}
