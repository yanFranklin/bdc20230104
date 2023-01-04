package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcOfdToImgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: realestate
 * @description: ofd转图片feign服务
 * @author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @create: 2022-08-24 09:11
 **/
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcOfdToImgFeignService extends BdcOfdToImgRestService {
}
