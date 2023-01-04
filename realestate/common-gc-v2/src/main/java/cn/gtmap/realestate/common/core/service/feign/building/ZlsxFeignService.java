package cn.gtmap.realestate.common.core.service.feign.building;

import cn.gtmap.realestate.common.core.service.rest.building.ZlsxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/27
 * @description 坐落刷新相关服务
 */
@FeignClient("${app.services.building-app:building-app}")
public interface ZlsxFeignService extends ZlsxRestService {
}
