package cn.gtmap.realestate.common.core.service.feign.rules;

import cn.gtmap.realestate.common.core.service.rest.rules.BdcGzZdbRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018-12-14
 * @description 不动产规则引擎字典表服务
 */
@FeignClient(name = "rules-app")
public interface BdcGzZdbFeignService extends BdcGzZdbRestService {
}
