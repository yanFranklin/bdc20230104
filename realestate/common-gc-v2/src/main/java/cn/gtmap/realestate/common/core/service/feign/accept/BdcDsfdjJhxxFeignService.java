package cn.gtmap.realestate.common.core.service.feign.accept;

import cn.gtmap.realestate.common.core.service.rest.accept.BdcDsfdjJhxxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/5
 * @description
 */
@FeignClient(name = "${app.services.accept-app:accept-app}")
public interface BdcDsfdjJhxxFeignService extends BdcDsfdjJhxxRestService {


}
