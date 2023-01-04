package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcXmFbRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: realestate
 * @description: 项目表附表feign服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-06-11 16:51
 **/
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcXmfbFeignService extends BdcXmFbRestService {
}
