package cn.gtmap.realestate.common.core.service.feign.engine;

import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzYzLogRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2022/05/13
 * @description 规则验证日志Feign
 */
@FeignClient(name = "${app.services.engine-app:engine-app}")
public interface BdcGzYzLogFeignService extends BdcGzYzLogRestService {
}
