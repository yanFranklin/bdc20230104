package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCdxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 查档信息feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-24 08:42
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlCdxxFeignService extends BdcSlCdxxRestService {
}
