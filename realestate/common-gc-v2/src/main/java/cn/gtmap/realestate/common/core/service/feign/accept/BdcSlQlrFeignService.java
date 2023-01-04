package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlQlrRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 不动产权利人Feign服务，提取accept中处理权利人信息通用接口服务供外部接口调用
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlQlrFeignService extends BdcSlQlrRestService {

}
