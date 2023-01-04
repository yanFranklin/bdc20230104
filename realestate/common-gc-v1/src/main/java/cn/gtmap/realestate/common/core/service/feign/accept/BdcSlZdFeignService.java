package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlZdRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2019/1/14
 * @description 不动产受理字典
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlZdFeignService extends BdcSlZdRestService {
}
