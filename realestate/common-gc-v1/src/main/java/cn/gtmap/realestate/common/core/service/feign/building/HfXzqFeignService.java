package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.HfXzqRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-31
 * @description 合肥行政区 读取服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface HfXzqFeignService extends HfXzqRestService{
}
