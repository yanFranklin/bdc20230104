package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.AcceptBdcdyRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface AcceptBdcdyFeignService extends AcceptBdcdyRestService{
}
