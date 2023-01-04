package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPrintRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/13,1.0
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcSlPrintFeignService extends BdcSlPrintRestService{
}
