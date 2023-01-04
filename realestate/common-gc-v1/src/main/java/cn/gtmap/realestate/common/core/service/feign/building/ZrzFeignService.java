package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.ZrzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢服务
 */
@FeignClient(name = "${app.services.building-app:building-app}")
public interface ZrzFeignService extends ZrzRestService{
}
