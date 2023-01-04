package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcXxbdRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/28
 * @description 信息比对接口
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcXxbdFeignService extends BdcXxbdRestService {
}
