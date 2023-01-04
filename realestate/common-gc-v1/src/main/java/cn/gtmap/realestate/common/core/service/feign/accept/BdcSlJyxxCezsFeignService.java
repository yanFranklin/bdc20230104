package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJyxxCezsRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/05/13
 * @description 不动产交易差额征收Feign接口
 */
@FeignClient("${app.services.accept-app:accept-app}")
public interface BdcSlJyxxCezsFeignService extends BdcSlJyxxCezsRestService {
}
