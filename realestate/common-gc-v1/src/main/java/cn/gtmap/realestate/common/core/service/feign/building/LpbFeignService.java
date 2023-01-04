package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.LpbRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-20
 * @description 楼盘表展现服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface LpbFeignService extends LpbRestService {
}
