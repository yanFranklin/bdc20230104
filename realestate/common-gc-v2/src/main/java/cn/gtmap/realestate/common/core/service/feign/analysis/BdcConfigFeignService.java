package cn.gtmap.realestate.common.core.service.feign.analysis;

import cn.gtmap.realestate.common.core.service.rest.analysis.BdcConfigRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/2
 * @description 配置项获取
 */
@FeignClient(value = "analysis-app" ,url = "127.0.0.1:8808")
public interface BdcConfigFeignService extends BdcConfigRestService {
}
