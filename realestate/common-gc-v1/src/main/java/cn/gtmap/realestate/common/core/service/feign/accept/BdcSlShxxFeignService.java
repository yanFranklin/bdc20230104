package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlShxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/2/23
 * @description 不动产需求流转审核rest服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlShxxFeignService extends BdcSlShxxRestService {
}
