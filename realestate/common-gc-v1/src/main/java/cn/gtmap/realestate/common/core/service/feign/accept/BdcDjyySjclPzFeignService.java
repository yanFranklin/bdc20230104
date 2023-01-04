package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjyySjclPzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 不动产登记原因收件材料配置feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 15:58
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcDjyySjclPzFeignService extends BdcDjyySjclPzRestService {
}
