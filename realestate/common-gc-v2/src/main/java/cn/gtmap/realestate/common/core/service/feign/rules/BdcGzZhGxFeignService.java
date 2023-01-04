package cn.gtmap.realestate.common.core.service.feign.rules;

import cn.gtmap.realestate.common.core.service.rest.rules.BdcGzZhGxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2018/12/15
 * @description 规则组合关系接口
 */
@FeignClient("rules-app")
public interface BdcGzZhGxFeignService extends BdcGzZhGxRestService {
}
