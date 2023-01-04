package cn.gtmap.realestate.common.core.service.feign.rules;

import cn.gtmap.realestate.common.core.service.rest.rules.BdcGzYwgzRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2018/12/13
 * @description 业务规则接口
 */
@FeignClient("rules-app")
public interface BdcGzYwgzFeignService extends BdcGzYwgzRestService {
}
