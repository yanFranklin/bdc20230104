package cn.gtmap.realestate.common.core.service.feign.init;


import cn.gtmap.realestate.common.core.service.rest.init.BdcHmdRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/10/25
 * @description 不动产黑名单Feign接口
 */
@FeignClient(name = "${app.services.init-app:init-app}")
public interface BdcHmdFeignService extends BdcHmdRestService {
}
