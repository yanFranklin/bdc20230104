package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.FwJtcyRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/30/16:41
 * @Description: 房屋家庭成员相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface FwJtcyFeignService extends FwJtcyRestService {
}
