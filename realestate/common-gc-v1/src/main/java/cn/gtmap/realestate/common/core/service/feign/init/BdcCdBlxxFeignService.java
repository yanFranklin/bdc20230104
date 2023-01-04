package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcCdBlxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 补录信息feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-24 09:11
 **/
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcCdBlxxFeignService extends BdcCdBlxxRestService {
}
