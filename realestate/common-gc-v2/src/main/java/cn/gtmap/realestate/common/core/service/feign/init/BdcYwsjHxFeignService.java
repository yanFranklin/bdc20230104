package cn.gtmap.realestate.common.core.service.feign.init;

import cn.gtmap.realestate.common.core.service.rest.init.BdcYwsjHxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/3/1
 * @description 不动产业务数据回写接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcYwsjHxFeignService extends BdcYwsjHxRestService {
}
