package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFpxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息Feign服务
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlFpxxFeignService extends BdcSlFpxxRestService {
}
