package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxmJmzcGxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: 收费项目减免政策关系feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:08
 **/
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlSfxmJmzcGxFeignService extends BdcSlSfxmJmzcGxRestService {
}
