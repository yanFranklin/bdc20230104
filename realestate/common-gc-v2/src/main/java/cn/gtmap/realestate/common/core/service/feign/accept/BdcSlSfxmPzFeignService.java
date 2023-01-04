package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxmPzRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2019/1/3
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlSfxmPzFeignService extends BdcSlSfxmPzRestService {
}
