package cn.gtmap.realestate.common.core.service.feign.accept;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 不动产登记小类配置rest服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcDjxlPzFeignService extends BdcDjxlPzRestService {
}
