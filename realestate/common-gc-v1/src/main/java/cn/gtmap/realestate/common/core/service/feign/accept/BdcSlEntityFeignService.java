package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlEntityRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/29
 * @description 实体公共操作FeignClient服务接口
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlEntityFeignService extends BdcSlEntityRestService {
}
