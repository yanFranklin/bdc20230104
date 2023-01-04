package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.NaturalResourcesRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/30 16:25
 * @description 自然资源部接口
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface NaturalResourcesFeignService extends NaturalResourcesRestService {
}
