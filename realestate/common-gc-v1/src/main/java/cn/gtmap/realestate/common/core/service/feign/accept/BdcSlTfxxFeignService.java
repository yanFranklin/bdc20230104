package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlTfxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/19
 * @description 不动产受理退费信息Feign接口
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlTfxxFeignService extends BdcSlTfxxRestService {
}
