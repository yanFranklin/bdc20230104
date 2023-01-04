package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCsjPzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 长三角业务数据配置feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 13:58
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlCsjPzFeignService extends BdcSlCsjPzRestService {
}
