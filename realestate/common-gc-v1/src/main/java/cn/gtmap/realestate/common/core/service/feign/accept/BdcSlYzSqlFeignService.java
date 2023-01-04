package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYzSqlRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @注释
 * @作者 gln
 * @创建日期 2019/5/28
 * @创建时间 9:18
 * @版本号 V 1.0
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlYzSqlFeignService extends BdcSlYzSqlRestService {
}
