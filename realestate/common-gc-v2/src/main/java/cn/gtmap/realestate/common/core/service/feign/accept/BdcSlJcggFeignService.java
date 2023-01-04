package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJcggRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 受理继承公告feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-01 16:15
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlJcggFeignService extends BdcSlJcggRestService {
}
