package cn.gtmap.realestate.common.core.service.feign.config;

import cn.gtmap.realestate.common.core.service.rest.config.BdcXtGgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/4/19
 * @description 不动产公告配置服务
 */
@FeignClient(name = "${app.services.realestate-config:realestate-config}")
public interface BdcXtGgFeignService extends BdcXtGgRestService {
}
