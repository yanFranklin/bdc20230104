package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSfxxRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/02
 * @description 不动产收费信息feign接口
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSfxxFeignService extends BdcSfxxRestService {
}
