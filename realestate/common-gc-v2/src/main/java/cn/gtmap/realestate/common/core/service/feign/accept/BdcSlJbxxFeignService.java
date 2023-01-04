package cn.gtmap.realestate.common.core.service.feign.accept;

import org.springframework.cloud.openfeign.FeignClient;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJbxxRestService;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/9
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlJbxxFeignService extends BdcSlJbxxRestService {
}
